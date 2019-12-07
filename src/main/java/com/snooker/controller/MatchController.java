package com.snooker.controller;

import com.snooker.domain.match.Match;
import com.snooker.domain.match.Schedule;
import com.snooker.domain.match.Session;
import com.snooker.exception.InnerException;
import com.snooker.service.StatsCache;
import com.snooker.service.MatchCrawlerService;
import com.snooker.service.MatchPosterService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author WangJunyu
 * @date 16/11/2
 * @e-mail iplusx@foxmail.com
 * @description 赛事信息接口
 */
@RestController
@RequestMapping("/api/v1/match")
public class MatchController extends BaseController{
    @Autowired
    private MatchCrawlerService matchCrawlerService;
    @Autowired
    private MatchPosterService matchPosterService;
    @Autowired
    private StatsCache statsCache;

    /**
     * 比分直播
     *
     * @return
     * @throws InnerException
     */
    @RequestMapping(value = "/livescore", method = RequestMethod.GET)
    public Object queryLiveScore() {
        List list = (List) statsCache.getLiveScore();
        return buildSuccessJson(list);
    }

    /**
     * 赛事列表
     *
     * @param season
     * @return
     * @throws InnerException
     */
    // @RequestMapping(value = "/list", method = RequestMethod.GET)
    // public JSONObject queryMatchList(String season) {
    //     Set<Match> matchList = matchCrawlerService.getMatchList(season);
    //     return buildSuccessJson(matchList);
    // }

    /**
     * 赛程信息
     *
     * @param id
     * @return
     * @throws InnerException
     */
    // @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    // public JSONObject getSchedule(String id) throws InnerException {
    //     List<Schedule> scheduleList = matchCrawlerService.getSchedule(id);
    //     return buildSuccessJson(scheduleList);
    // }

    /**
     * 获取今日比赛
     *
     * @return
     * @throws InnerException
     */
    // @RequestMapping(value = "/today", method = RequestMethod.GET)
    // public JSONObject getToday() throws InnerException {
    //     List<Session> sessionList = statsCache.getTodaySchedule();
    //     return buildSuccessJson(sessionList);
    // }

    /**
     * 存放赛事海报
     *
     * @param request
     * @return
     */
    // @RequestMapping(value = "/poster", method = RequestMethod.POST)
    // public JSONObject putPoster(HttpServletRequest request) {
    //     String season = request.getParameter("season");
    //     assert season != null;
    //     Map<String, String[]> map = request.getParameterMap();
    //     for (String matchId : map.keySet()) {
    //         matchPosterService.putPoster(matchId, map.get(matchId)[0]);
    //     }

    //     matchPosterService.emptyListCache(season);
    //     return buildSuccessJson();
    // }

    /**
     * 获取对阵详细数据，带比分直播
     *   from m.fri.tv
     *
     * @param dzid
     * @param date
     * @return
     * @throws InnerException
     */
    // @RequestMapping(value = "/detail", method = RequestMethod.GET)
    // public JSONObject getMatchDetail(String dzid, String date) throws InnerException {
    //     return buildSuccessJson(JSONObject.fromObject(matchCrawlerService.getMatchDetail(dzid, date)));
    // }

    /**
     * 获取对阵列表
     *   非本赛季的时候显示这个，而不是赛程
     *
     * @param matchId
     * @return
     * @throws InnerException
     */
    // @RequestMapping(value = "/againstList", method = RequestMethod.GET)
    // public JSONObject getAgainstList(String matchId) throws InnerException {
    //     return buildSuccessJson(matchCrawlerService.getAgainstList(matchId), new String[] {"grab", "time"});
    // }
}
