package com.snooker.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final int EXPIRE_MILLS = 600000; //token有效期，毫秒
    public static Map<String, Long> tokenPool = new HashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            String token = request.getHeader("token");
            boolean tokenIsValid = StringUtils.isNotBlank(token)
                    && tokenPool.containsKey(token)
                    && (tokenPool.get(token) + EXPIRE_MILLS) > System.currentTimeMillis();
            if (tokenIsValid) {
                tokenPool.put(token, System.currentTimeMillis());
            }
            else {
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
            return tokenIsValid;
    }
}
