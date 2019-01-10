package com.snooker.service;

import com.snooker.domain.match.Against;
import com.snooker.exception.InnerException;
import com.snooker.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.snooker.util.JsoupUtil.getElements;

/**
 * Authored by WangJunyu on 2017/4/11
 */
@Service
public class MatchAgainstCacheService {
    @Value("${crawler.match.against}")
    private String againstUrl;
    @Value("${crawler.match.list}")
    private String listUrl;

    @Autowired
    private TimeUtil timeUtil;

    List<Against> getAgainst(String id) {
        Elements againsts = getElements(againstUrl, "div.against", id);
        Elements tables = againsts.select("table");

        Elements divs = againsts.select("div.against-til");
        Map<String, Integer> map = new HashMap<>();
        for (Element div : divs) {
            String round = div.select("span").text();
            String text = div.select("i").text();
            int grab = 0;
            if (StringUtils.isNotBlank(text)) {
                grab = Integer.parseInt(text.substring(text.indexOf("局")+1, text.indexOf("胜")));
            }
            map.put(round, grab);
        }

        List<Against> againstList = new ArrayList<>();
        for (Element table : tables) {
            Elements tds = table.select("td");
            String thatNo = tds.get(0).select("i").text();
            Elements ps = tds.get(1).select("p");
            String score = tds.get(1).select("span").text().trim();
            String p1name = ps.get(0).text();
            String p2name = ps.get(1).text();
            String p1set = score.substring(0, score.indexOf(" "));
            String p2set = score.substring(score.lastIndexOf(" ") + 1);
            String round = tds.get(0).text();
            round = round.substring(round.indexOf(thatNo) + thatNo.length());
            int grab = map.get(round);

            String dzid = "";
            Elements elements = tds.select(".icon01-active");
            if (elements != null && elements.size() > 0) {
                dzid = elements.get(0).attr("onclick");
                dzid = dzid.substring(dzid.indexOf("(")+1, dzid.indexOf(")")).replace("'","");
                String[] split = dzid.split(",");
                dzid = split[5];
            }

            Against against = new Against(thatNo, p1name, p1set, p2name, p2set, grab, dzid);
            againstList.add(against);
        }
        return againstList;
    }

    @Cacheable(value = "match:against", key = "'match:against:'+#matchId")
    public Map<String, String> getDzid(String matchId) {
        Map<String, String> map = new HashMap<>();
        List<Against> againstList = getAgainst(matchId);
        for (Against against : againstList) {
            map.put(against.getNo(), against.getDzid());
        }
        return map;
    }

    @Cacheable(value = "match:fighting:id", key = "'match:fighting:id'")
    public String getFightingId() throws InnerException {
        Elements matches = getElements(listUrl, "div.match-right-block ul.match-list li");
        if (CollectionUtils.isEmpty(matches)) {
            throw new InnerException(InnerException.NO_MATCH);
        }
        for (Element match : matches) {
            String time = match.select("p.txtTime").text();
            String begin = time.substring(0, time.indexOf("-"));
            Date beginDate = timeUtil.stringToDate(begin, new SimpleDateFormat("yyyy.MM.dd"));
            if (beginDate.before(new Date())) {
                String href = match.select("a").attr("href");
                return href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));
            }
        }
        return null;
    }
}
