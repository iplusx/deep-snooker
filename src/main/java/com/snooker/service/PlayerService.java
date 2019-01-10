package com.snooker.service;

import com.snooker.domain.player.FriPlayerRanking;
import com.snooker.domain.player.PlayerAgainstData;
import com.snooker.domain.player.PlayerInfo;
import com.snooker.domain.player.Ranking;
import com.snooker.util.HttpUtil;
import com.snooker.util.JsoupUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Authored by WangJunyu on 2017/4/6
 */
@Service
public class PlayerService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${crawler.player.ranking}")
    private String RANKING_URL;
    @Value("${crawler.player.info}")
    private String INFO_URL;
    @Value("${crawler.player.pk}")
    private String PK_URL;
    @Value("${crawler.player.nameList}")
    private String NAME_LIST_URL;
    @Autowired
    private GetSyncName getSync;
    @Autowired
    private HttpUtil httpUtil;

    private static final String WEB_URL = "http://www.fri.tv";

    @Cacheable(value = "player:ranking", key = "'player:ranking'")
    public List<Ranking> getPlayerWorldRanking() {
        // 先从缓存拿到官网英文名字对应的星期五上的球员信息
        Map<String, FriPlayerRanking> sync = getSync.getCache();
        List<Ranking> rankingList = new ArrayList<>();
        // 获取排行列表，每个tr标签是一个球员
        Elements trs = JsoupUtil.getElements(RANKING_URL, ".row-hover tr");
        for (Element tr : trs) {
            // 每个td分别是一个信息
            Elements tds = tr.select("td");
            // 球员的英文名字
            String enName = tds.get(1).text();
            enName = enName.replace(" *", "");
            // 星期五的球员数据
            FriPlayerRanking friPlayerRanking = sync.get(enName);
            // 最终的数据
            String uid = null;
            String cnName = enName;
            String nationality = null;
            // 有映射数据就从映射里拿
            if (friPlayerRanking != null) {
                uid = friPlayerRanking.getUid();
                cnName = friPlayerRanking.getName();
                nationality = friPlayerRanking.getNationality();
            }
            else {
                logger.warn("Unknown player {}", enName);
                putSync();
                friPlayerRanking = sync.get(enName);
                uid = friPlayerRanking.getUid();
                cnName = friPlayerRanking.getName();
            }
            // 封装
            rankingList.add(new Ranking(tds.first().text(), cnName , tds.get(2).text(), uid, nationality));
        }
        return rankingList;
    }

    private List<String> getOfficialRankingName() {
//        List<String> enNames = new ArrayList<>();
        // 获取排行列表，每个tr标签是一个球员
        Elements trs = JsoupUtil.getElements(RANKING_URL, ".row-hover tr");
        //        for (Element tr : trs) {
//            // 球员的英文名字
//            String enName = tr.select("td").get(1).text();
//            enNames.add(enName);
//        }
        return trs.stream().map(tr -> tr.select("td").get(1).text())
                .collect(Collectors.toList());
    }

    private List<FriPlayerRanking> getRankingOnFri() {
        List<FriPlayerRanking> infos = new ArrayList<>();
        String url = "http://player.fri.tv/playerinfo.aspx?currpage={0}";
        for (int i = 1; i <= 7 ; i++) {
            Elements trs = JsoupUtil.getElements(url, ".playTab tr", i);
            trs.remove(0);
            for (Element tr : trs) {
                Element td = tr.select(".til").first();
                String name = td.select("p").first().text();
                String s = td.attr("onclick");
                String uid = s.substring(s.indexOf("=")+1, s.lastIndexOf("'"));
                String nationality = td.nextElementSibling().text();
                infos.add(new FriPlayerRanking(name, uid, nationality));
            }
        }
        return infos;
    }

    @CachePut(value = "player:name", key = "'player:name'")
    public Map<String, FriPlayerRanking> putSync() {
        Map<String, FriPlayerRanking> map = new HashMap<>();
        List<String> officialEnNameList = getOfficialRankingName();
        List<FriPlayerRanking> friPlayerRankingList = getRankingOnFri();
        for (int i = 0; i < officialEnNameList.size(); i++) {
            String enName = officialEnNameList.get(i);
            enName = enName.replace(" *", "");
            FriPlayerRanking friPlayerRanking = friPlayerRankingList.get(i);
            map.put(enName, friPlayerRanking);
        }
        return map;
    }

    public PlayerInfo getPlayerInfo(String uid) {
        Element wholeMsg = JsoupUtil.getElements(INFO_URL, ".w1222", uid).get(1);
        Elements basic = wholeMsg.select(".recordPlayer p");
        String country = basic.get(1).text();
        String birthday = basic.get(2).text();
        String turnedPro = basic.get(3).text();
        String rank = basic.get(4).text();
        String desc= basic.get(5).text();
        Element element = wholeMsg.select(".recordPlayer .playerPic").first();
        Element img = element.select("img").first();
        String avatarUrl = WEB_URL + img.attr("src");
        String name = element.select("p").first().childNode(0).toString();

        Elements data = wholeMsg.select(".against span");
        String titles = data.get(0).text();
        String largeTitles = data.get(1).text();
        String microTitles = data.get(2).text();
        String inviteTitles = data.get(3).text();
        String centuryBreak = data.get(6).text();
        String maxBreak = data.get(10).text();

        return new PlayerInfo(name, avatarUrl, country, birthday, turnedPro, rank, titles,
                largeTitles, microTitles, inviteTitles, centuryBreak, maxBreak, desc);

    }

    public JSONObject getPKData(String p1name, String p2name) {
        Elements data = JsoupUtil.getElements(PK_URL, ".w1222", p1name, p2name);
        PlayerAgainstData p1data = getOnePKData(data, ".pkVs .playerLeft", ".pkBox .pct .pkRed", ".pkList .pkRed");
        PlayerAgainstData p2Data = getOnePKData(data, ".pkVs .playerRight", ".pkBox .pct .pkBlue", ".pkList .pkBlue");
        JSONObject json = new JSONObject();
        json.put("p1", p1data);
        json.put("p2", p2Data);
        return json;
    }

    private PlayerAgainstData getOnePKData(Elements data, String... cssQuery) {
        Element playerInfo =  data.select(cssQuery[0]).first();
        String uid = playerInfo.select("a").first().attr("href");
        uid = uid.substring(uid.indexOf("=")+1);
        String avatarUrl = WEB_URL + playerInfo.select("img").first().attr("src");
        String name = playerInfo.select("p").first().text();
        name = name.substring(0, name.indexOf(" "));

        Element pkData = data.select(cssQuery[1]).first();
        String pkWinRatio = pkData.attr("style");
        pkWinRatio = pkWinRatio.substring(7);
        String pkWinTimes = pkData.text();

        Elements dataList = data.select(cssQuery[2]);
        String match = dataList.get(0).text();
        String title = dataList.get(1).text();
        String session = dataList.get(2).text();
        String sessionRatio = dataList.get(3).text();
        String set = dataList.get(4).text();
        String setRatio = dataList.get(5).text();
        String century = dataList.get(6).text();
        String centuryRatio = dataList.get(7).text();
        String decider = dataList.get(8).text();
        String deciderRatio = dataList.get(9).text();
        String maximum = dataList.get(10).text();
        String kill = dataList.get(11).text();

        return new PlayerAgainstData(name, pkWinRatio, pkWinTimes, match, title, session, set, century,
                maximum, kill, decider, sessionRatio, setRatio, centuryRatio, deciderRatio, uid, avatarUrl);

    }

    public List<String> queryByName(String word) {
        List<String> list = new ArrayList<>();
        String url = MessageFormat.format(NAME_LIST_URL, word);
        JSONObject resp = JSONObject.fromObject(httpUtil.getDataFromUrl(url));
        JSONArray ls = resp.getJSONArray("list");
        for (Object l : ls) {
            JSONObject json = JSONObject.fromObject(l);
            list.add(json.getString("cnname"));
        }
        return list;
    }
}
