package com.snooker.service;

import com.snooker.domain.match.Against;
import com.snooker.domain.match.Match;
import com.snooker.domain.match.Schedule;
import com.snooker.domain.match.Session;
import com.snooker.exception.InnerException;
import com.snooker.util.HttpUtil;
import com.snooker.util.JsoupUtil;
import com.snooker.util.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.snooker.util.JsoupUtil.getElements;

/**
 * @author WangJunyu
 * @date 16/11/11
 * @e-mail iplusx@foxmail.com
 * @description
 */
@Service
public class MatchCrawlerService {
    private static final String DETAIL_DATE = "2011%2f11%2f11+11%3a11%3a11";

    @Value("${crawler.match.schedule}")
    private String scheduleListUrl;

    @Value("${crawler.match.info}")
    private String infoUrl;
    @Value("${crawler.match.against}")
    private String againstUrl;
    @Value("${crawler.match.dz}")
    private String dzUrl;
    @Value("${crawler.match.matchdetail}")
    private String matchDetailUrl;
    @Value("${crawler.match.singlelive}")
    private String singleLiveUrl;
    @Value("${crawler.match.list}")
    private String listUrl;

    @Autowired
    private TimeUtil timeUtil;
    @Autowired
    private MatchPosterService matchPosterService;
    @Autowired
    private MatchAgainstCacheService matchAgainstCacheService;
    @Autowired
    private HttpUtil httpUtil;

    @Cacheable(value = "match:list", key = "'match:list:'+#season")
    public Set<Match> getMatchList(String season) {
        int year = Integer.parseInt(season);
        TreeSet<Match> matchList = new TreeSet<>();
        String today = timeUtil.getDateString(new Date(), new SimpleDateFormat("yyyy.MM.dd"));
        Elements trs = getMatchList(season, "大型排名赛", season);
        trs.addAll(getMatchList(season, "邀请赛", season));
        trs.addAll(getMatchList(season, "大型排名赛", Integer.toString(year+1)));
        trs.addAll(getMatchList(season, "邀请赛", Integer.toString(year+1)));
        for (Element tr : trs) {
            //第一个a标签
            Element a = tr.select(".til a").first();
            //链接
            String href = a.attr("href");
            //id
            String id = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));
            //名称
            String name = a.text();
            Elements tds = tr.select("td");
            //持续时间  yyyy.MM.dd-yyyy.MM.dd
            String duration = tds.last().text();
            //实例化对象
            Match match = new Match();
            match.setId(id);
            match.setName(name.substring(7));
            match.setTime(duration);
            match.setType(tds.get(2).text());
            match.setNumber(tds.get(3).text());
            match.setPosterUrl(matchPosterService.getPoster(id));

            String[] times1 = duration.split("-");
            String begin = times1[0];
            String end = times1[1];
            if (begin.compareTo(today) > 0) {
                match.setStatus((byte) 1);
            }
            else if (end.compareTo(today) >= 0) {
                match.setStatus((byte) 0);
            }
            else {
                match.setStatus((byte) -1);
            }
            matchList.add(match);
        }
        return matchList;
    }

    public List<Schedule> getSchedule(String id) throws InnerException {
        // 先从缓存拿到no-dzid的map，放到赛程里，这样点击某个对局可以跳转到详细对阵数据里
        Map<String, String> dzidCache = matchAgainstCacheService.getDzid(id);

        List<Schedule> scheduleList = new ArrayList<>();
        //抽取赛程数据
        Elements list = getElements(scheduleListUrl, "div.schedule table tr", id);
        //去掉列头
        list.remove(0);

        //以当天的比赛场次数量为间隔遍历整个赛程,可以理解为按天分成一段段
        for (int i = 0; i < list.size();) {
            Schedule schedule = new Schedule();
            //每段的第一个td标签下包含比赛日期信息
            Element firstTd = list.get(i).select("td").first();
            String date = firstTd.text().substring(0, firstTd.text().indexOf("星期") - 1);
            String day = firstTd.text().substring(firstTd.text().indexOf("星期"));
            schedule.setDate(date);
            schedule.setDay(day);

            //获取当天的比赛场次数量
            int offst = Integer.parseInt(firstTd.attr("rowspan"));
            List<Session> sessionList = new ArrayList<>();
            //遍历当天的场次
            for (int j = 0; j < offst; j++) {
                Session session = new Session();
                //游标,在整个赛程中定位到指定的场次
                int cursor = i + j;
                Element tr = list.get(cursor);
                //对局的一些数据
                Elements tds = tr.select("td");
                if (j == 0) {
                    //只有每个场次的第一个td标签会包含比赛日期信息,去掉.这样会保证所有的场次都具有相同的结构
                    tds.remove(0);
                }

                //场次的具体开始时间,轮次
                session.setTime(tds.first().text());
                session.setRound(tds.get(1).text());

                //序号
                Elements nos = tds.get(2).select("p");
                //球员姓名
                Elements names = tds.get(3).select("p");
                //比分
                Elements scores = tds.get(3).select("i");

                List<Against> againstList = new ArrayList<>();
                //每个序号都对应一个对局,根据这个遍历所有的对局
                for (int k = 0; k < nos.size(); k++) {
                    Against against = new Against();
                    if (!StringUtils.isEmpty(nos.get(k).text())) {  //有序号还没定下,为空的情况
                        //构造对局对象
                        against.setNo(nos.get(k).text());
                        against.setDzid(dzidCache.get(Integer.toString(Integer.parseInt(against.getNo()))));
                        against.setP1name(names.get(k*2).text().replace("[直播]", ""));
                        against.setP1set(scores.get(k*2).text());
                        against.setP2name(names.get(k*2+1).text().replace("[直播]", ""));
                        against.setP2set(scores.get(k*2+1).text());
                    }
                    //添加到对局列表中
                    againstList.add(against);
                }
                //对局列表添加到该天该场中
                session.setAgainstList(againstList);
                //场次添加到场次列表中
                sessionList.add(session);
            }
            //场次列表添加到赛程中
            schedule.setSessionList(sessionList);
            //赛程添加到赛程列表中
            scheduleList.add(schedule);

            //间隔数,以此分段
            i += offst;
        }
        return scheduleList;
    }

    public List<Session> getScheduleByDate(String id, String date) throws InnerException {
        //遍历全部的赛程
        List<Schedule> originalSchedule = getSchedule(id);
        for (Schedule schedule : originalSchedule) {
            //取到指定日期的
            if (schedule.getDate().equals(date)) {
                return schedule.getSessionList();
            }
        }
        return Collections.emptyList();
    }

    /**
     * 获取赛事列表相关的元素
     *
     * @param season
     * @param bisaicate
     * @param year
     * @return
     * @throws InnerException
     */
    private Elements getMatchList(String season, String bisaicate, String year) {
        Elements elements = new Elements();
        //获取赛事列表
        for (int page = 1; page < 4; page++) {
            Elements list = getElements(listUrl, "div.matchlist table tr", year, bisaicate, page);
            list.remove(0);
            for (Element tr : list) {
                if (tr.select("td").get(1).text().startsWith(season)) {
                    elements.add(tr);
                }
            }
        }
        return elements;
    }

    @Cacheable(value = "match:detail", key = "'match:detail:'+#dzid")
    public String getMatchDetail(String dzid, String date) {
        Date d = null;
        try {
            if (date != null) {
                d = TimeUtil.YMDHM_FORMAT.parse(date);
                SimpleDateFormat friFomater = new SimpleDateFormat("yyyy/M/dd H:mm:ss");
                date = friFomater.format(d);
                date = URLEncoder.encode(date, "utf-8");
            }
            else {
                date = DETAIL_DATE;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = MessageFormat.format(matchDetailUrl, dzid, date);
        String resp = httpUtil.getDataFromUrl(url);
        JSONObject json = JSONObject.fromObject(resp);
        if (json != null) {
            String p1en = json.getString("player1en").replace(" ", "+");
            String p2en = json.getString("player2en").replace(" ", "+");
            resp = httpUtil.getDataFromUrl(MessageFormat.format(singleLiveUrl, dzid, p1en, p2en));
            if (!StringUtils.isEmpty(resp)) {
                json.put("live", JSONObject.fromObject(resp));
            }
        }

        return json.toString();
    }

    public List<Session> getAgainstList(String matchId) {
        List<Session> sessionList = new ArrayList<>();
        Elements divs = JsoupUtil.getElements(againstUrl, ".against-til", matchId);

        for (Element div : divs) {
            Session session = new Session();
            session.setRound(div.select("span").text());

            Element table = div.nextElementSibling();
            List<Against> againstList = new ArrayList<>();
            while (table != null && table.tagName().equals("table")) {
                Element tr = table.select("tr").first();
                Elements tds = tr.getElementsByTag("td");
                Element td0 = tds.get(0);
                String no = td0.getElementsByTag("i").text();

                Element td1 = tds.get(1);
                String p1name = td1.select("p").first().text();
                String p2name = td1.select("p").get(1).text();
                String set = td1.select("span").text();
                String p1set = set.substring(0, set.indexOf(" "));
                String p2set = set.substring(set.lastIndexOf(" ")+1);
                Elements icons = tds.select(".icon01-active");
                String dzid = null;
                if (icons != null && icons.size() > 0) {
                    String onclick = icons.first().attr("onclick");
                    dzid = StringUtils.isEmpty(onclick) ? "" : onclick.split(",")[5].replace("'","");
                }
                Against against = new Against();
                against.setNo(no);
                against.setP1name(p1name);
                against.setP2name(p2name);
                against.setDzid(dzid);
                against.setP1set(p1set);
                against.setP2set(p2set);
                againstList.add(against);

                table = table.nextElementSibling();
            }
            session.setAgainstList(againstList);
            sessionList.add(session);
        }

        return sessionList;
    }

    public List<Session> getWapToday() {
        String url = "http://api.fri.tv/api/match/matchListByDate/";
        String dataFromUrl = httpUtil.getDataFromUrl(url);
        JSONObject json = JSONObject.fromObject(dataFromUrl).getJSONArray("list").getJSONObject(0);
        JSONArray dataList = json.getJSONArray("datalist");

        String time1 = dataList.getJSONObject(0).getString("matchdateshort");
        String time2 = dataList.getJSONObject(2).getString("matchdateshort");
        String round1 = dataList.getJSONObject(0).getString("luncititle");
        String round2 = dataList.getJSONObject(2).getString("luncititle");

        List<Against> againstList = new ArrayList<>();
        for (Object data : dataList) {
            JSONObject dataJson = JSONObject.fromObject(data);
            Against against = new Against();
            against.setDzid(dataJson.getString("dzid"));
            against.setP1name(dataJson.getString("player1"));
            against.setP2name(dataJson.getString("player2"));
            against.setP1set(dataJson.getString("player1fen"));
            against.setP2set(dataJson.getString("player2fen"));
            againstList.add(against);
        }

        Session session1 = new Session();
        session1.setTime(time1);
        session1.setRound(round1);
        session1.setAgainstList(againstList.subList(0, 2));
        Session session2 = new Session();
        session2.setTime(time2);
        session2.setRound(round2);
        session2.setAgainstList(againstList.subList(2, 4));
        List<Session> sessionList = new ArrayList<>();
        sessionList.add(session1);
        sessionList.add(session2);
        return sessionList;
    }
}
