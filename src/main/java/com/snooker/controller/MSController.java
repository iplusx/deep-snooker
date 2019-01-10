package com.snooker.controller;

import com.snooker.domain.match.Match;
import com.snooker.security.LoginInterceptor;
import com.snooker.service.MatchCrawlerService;
import com.snooker.service.MatchPosterService;
import com.snooker.service.SettingService;
import com.snooker.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "")
public class MSController extends BaseController{
    @Value("${ms.admin.account}")
    private String account;
    @Value("${ms.admin.password}")
    private String password;

    @Autowired
    private MatchCrawlerService matchCrawlerService;
    @Autowired
    private MatchPosterService matchPosterService;
    @Autowired
    private SettingService settingService;

    @PostMapping("/login")
    public Object login(@RequestBody Map<String, String> map,
                        HttpServletResponse response) {
        if (map.get("account").equals(account)
                && MD5Utils.md5(map.get("password")).equals(password)) {
            LoginInterceptor.tokenPool.put(map.get("account"), System.currentTimeMillis());
            return buildSuccessJson();
        }
        else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }

    @GetMapping("/t/{season}")
    public Object getTournaments(@PathVariable("season") int season) {
        Set<Match> matchList = matchCrawlerService.getMatchList(Integer.toString(season));
        return buildSuccessJson(matchList, new String[] {"time", "type", "number", "status"});
    }

    @PostMapping("/t/poster")
    public Object uploadPoster(@RequestBody Map<String, String> map) {
        matchPosterService.putPoster(map.get("id"), map.get("posterUrl"));
        matchPosterService.emptyListCache(map.get("season"));
        return buildSuccessJson();
    }

    @GetMapping("/setting/liveScoreUrl")
    public Object getLiveScoreUrl() {
        return buildSuccessJson(settingService.getLiveScoreUrl());
    }

    @PostMapping("/setting/liveScoreUrl")
    public Object setLiveScoreUrl(@RequestBody Map<String, String> map) {
        settingService.putLiveScoreUrl(map.get("url"));
        return buildSuccessJson();
    }
}
