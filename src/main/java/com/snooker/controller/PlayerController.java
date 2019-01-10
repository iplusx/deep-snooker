package com.snooker.controller;

import com.snooker.exception.InnerException;
import com.snooker.service.PlayerService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authored by WangJunyu on 2017/4/7
 */
@RestController
@RequestMapping(value = "/api/v1/player")
public class PlayerController extends BaseController{
    @Autowired
    private PlayerService playerService;

    @RequestMapping(value = "/ranking", method = RequestMethod.GET)
    public JSONObject ranking() {
        return buildSuccessJson(playerService.getPlayerWorldRanking());
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public JSONObject getPlayerInfo(String uid) throws InnerException {
        return buildSuccessJson(playerService.getPlayerInfo(uid));
    }

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    public JSONObject sync() {
        playerService.putSync();
        return buildSuccessJson();
    }

    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public JSONObject getPkData(String p1name, String p2name) {
        return playerService.getPKData(p1name, p2name);
    }

    @RequestMapping(value = "/names", method = RequestMethod.GET)
    public JSONObject getNameList(String word) {
        return buildSuccessJson(playerService.queryByName(word));
    }
}
