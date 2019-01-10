package com.snooker.controller;


import com.snooker.exception.InnerException;
import com.snooker.util.JsonDeserializeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author WangJunyu
 * @date 16/11/11
 * @e-mail iplusx@foxmail.com
 * @description Controller基类
 */
public class BaseController {
    private static final int SUCCESS_CODE = 0;
    private static final String SUCCESS_MSG = "ok";

    /**
     * 构造成功响应json
     *
     * @param object
     * @return
     */
    public JSONObject buildSuccessJson(Object object) {
        return buildSuccessJson(object, new JsonConfig());
    }

    public JSONObject buildSuccessJson(Object object, JsonConfig config) {
        config.registerJsonValueProcessor(Date.class, new JsonDeserializeUtil());
        JSONObject resp = new JSONObject();
        resp.put("code", SUCCESS_CODE);
        resp.put("msg", SUCCESS_MSG);
        if (object instanceof List || object instanceof Page || object instanceof Set) {
            resp.put("data", JSONArray.fromObject(object, config));
        }
        else if (object instanceof String || object instanceof Integer) {
            resp.put("data", object);
        }
        else {
            resp.put("data", JSONObject.fromObject(object, config));
        }
        return resp;
    }

    public JSONObject buildSuccessJson(Object object, String[] excludes) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setExcludes(excludes);
        return buildSuccessJson(object, jsonConfig);
    }

    public JSONObject buildSuccessJson() {
        JSONObject resp = new JSONObject();
        resp.put("code", SUCCESS_CODE);
        resp.put("msg", SUCCESS_MSG);
        return resp;
    }

    /**
     * 捕获服务器内部异常,构造失败响应json
     *
     * @param ie
     * @return
     */
    @ExceptionHandler(InnerException.class)
    public JSONObject buildErrorJson(InnerException ie) {
        JSONObject resp = new JSONObject();
        resp.put("code", ie.getErrcode());
        resp.put("msg", ie.getMessage());
        resp.put("data", "");
        return resp;
    }

//    @RequestMapping(value = "/error", method = RequestMethod.GET)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "just for development")
//    public String error5x() {
//        return "error";
//    }

    @RequestMapping(value = "/api/v1/error/net", method = RequestMethod.GET)
    public String error() throws InnerException {
        throw new InnerException(InnerException.SCRAWLER_CONNECT_ERROR);
    }

    @RequestMapping(value = "/api/v1/error", method = RequestMethod.GET)
    public String ex() throws Exception {
        throw new Exception();
    }
}
