package com.snooker.service;

import com.snooker.domain.match.LiveScore;
import com.snooker.domain.match.ScorePanelCommon;
import com.snooker.domain.match.ScorePanelPlayer;
import com.snooker.domain.match.Session;
import com.snooker.exception.InnerException;
import com.snooker.util.TimeUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.snooker.util.JsoupUtil.getElements;

/**
 * Description:
 *
 * @author Junyu Wang
 */
@Service
public class StatsCache {
    private static final String LIVESCORES_URL = "https://livescores.worldsnookerdata.com";
    private static final Logger logger = LoggerFactory.getLogger(StatsCache.class);

    @Autowired
    private MatchAgainstCacheService matchAgainstCacheService;
    @Autowired
    private MatchCrawlerService matchCrawlerService;
    @Autowired
    private TimeUtil timeUtil;
    @Autowired
    private SettingService settingService;

    @CachePut(value = "match:liveScore", key = "'liveScore'")
    public List<LiveScore> putLiveScore() {
        return this.fetchLiveScore();
    }

    @Cacheable(value = "match:liveScore", key = "'liveScore'")
    public Object getLiveScore() {
        return this.fetchLiveScore();
    }

    @CachePut(value = "schedule:today", key = "'schedule:today'")
    public Object putTodaySchedule() {
        return this.fetchTodaySchedule();
    }

    @Cacheable(value = "schedule:today", key = "'schedule:today'")
    public List<Session> getTodaySchedule() {
        return this.fetchTodaySchedule();
    }

    private List<Session> fetchTodaySchedule() throws InnerException {
        logger.info("爬取今日赛程开始...");
        long startAt = System.currentTimeMillis();
        String id = null;
        try {
            id = matchAgainstCacheService.getFightingId();
        } catch (InnerException e) {
            if (e.getErrcode() == InnerException.NO_MATCH) {
                return Collections.emptyList();
            }
        }
        String date = timeUtil.getDateString(new Date(), TimeUtil.YMD_FORMAT);
        List<Session> schedule = matchCrawlerService.getScheduleByDate(id, date);
        logger.info("爬取今日赛程结束，耗时：{}ms", System.currentTimeMillis() - startAt);
        return schedule;
    }

    private List<LiveScore> fetchLiveScore() {
        logger.info("爬取比分直播开始...");
        long startAt = System.currentTimeMillis();
        Elements viewGameBtns = getElements(LIVESCORES_URL + settingService.getLiveScoreUrl(), "#iface-content .live-match-item .live-match-item-footer .text-btn");
        List<LiveScore> liveScoreList = new ArrayList<>();
        for (Element btn : viewGameBtns) {
            String tableUrl = btn.attr("href");
            tableUrl = LIVESCORES_URL + tableUrl;
            Elements cols = getElements(tableUrl, ".live-match div.row div");
            Element left = cols.get(0);
            Element middle = cols.get(1);
            Element right = cols.get(2);

            //分别获取两边选手的比分
            ScorePanelPlayer player1 = new ScorePanelPlayer();
            player1.setName(left.select("span.live-match-val-player1").first().text());
            player1.setUid("122");
            player1.setHeadImgUrl(left.select("img.live-match-player1-img").attr("src"));

            ScorePanelPlayer player2 = new ScorePanelPlayer();
            player2.setName(right.select("span.live-match-val-player2").first().text());
            player2.setUid("23");
            player2.setHeadImgUrl(right.select("img.live-match-player2-img").attr("src"));

            Elements scores = middle.select("tbody tr");
            Element tr0 = scores.get(0);
            Element tr1 = scores.get(1);
            Element tr2 = scores.get(2);
            Element tr3 = scores.get(3);
            Element tr4 = scores.get(4);

            player1.setTotalSetScore(tr0.getElementsByTag("td").first().text());
            player2.setTotalSetScore(tr0.getElementsByTag("td").last().text());
            player1.setThisSetScore(tr2.getElementsByTag("td").first().text());
            player2.setThisSetScore(tr2.getElementsByTag("td").last().text());
            player1.setBreakScore(tr4.getElementsByTag("td").first().text());
            player2.setBreakScore(tr4.getElementsByTag("td").last().text());

            ScorePanelCommon common = new ScorePanelCommon();
            common.setBestOf(tr1.getElementsByTag("td").first().text().replace("Best of ", ""));
            common.setLeftScore(tr3.getElementsByTag("td").first().text().replace(" points left", ""));
            common.setUrl(tableUrl);
            //实例化对象并添加到列表中
            liveScoreList.add(new LiveScore(player1, player2, common));
        }
        logger.info("爬取比分直播结束，耗时：{}ms", System.currentTimeMillis() - startAt);
        return liveScoreList;
    }
}
