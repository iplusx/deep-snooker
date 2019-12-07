package com.snooker.job;

import com.snooker.service.StatsCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author Junyu Wang
 */
@Service
public class FetchDataJob {
    private static final Logger logger = LoggerFactory.getLogger(FetchDataJob.class);

    @Autowired
    private StatsCache statsCache;

    @Scheduled(cron = "0/15 * * * * ?")
    public void fetchLiveScoreJob() {
        logger.info("*****定时任务，爬取比分直播开始*****");
        statsCache.putLiveScore();
        logger.info("*****定时任务，爬取比分直播结束*****");
    }

    // @Scheduled(cron = "0 0/2 * * * ?")
    public void fetchTodaySchedule() {
        logger.info("*****定时任务，爬取今日赛程开始*****");
        statsCache.putTodaySchedule();
        logger.info("*****定时任务，爬取今日赛程结束*****");
    }
}
