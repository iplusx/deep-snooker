package com.snooker.filter;

import com.snooker.exception.InnerException;
import com.snooker.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author by WangJunyu on 17/2/14
 *
 * 过滤微信服务器以外的请求
 */
@Configuration
public class WxFilter implements Filter{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${verify.default_referer}")
    private String defaultReferer;
    @Value("${verify.custom_token}")
    private String customToken;

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String referer = request.getHeader("referer");
        String zijiren = request.getHeader("zijiren");
        if ((referer!= null && referer.startsWith(defaultReferer)) ||
                (zijiren != null && zijiren.equalsIgnoreCase(customToken))) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            try {
                logger.info("拦截到非法请求，ip={}\treferer={}", RequestUtil.getIpAddress(request), referer);
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.setStatus(HttpStatus.FORBIDDEN.value());
            } catch (InnerException e) {
            }
        }
    }

    @Override
    public void destroy() {

    }
}
