package com.banma.web.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lichaofu
 * @create 2021-04-27 19:13
 */
@Configuration
@Order(1)
@WebFilter(filterName = "logFilter", urlPatterns = "/*")
public class LogFilter implements Filter {
    @Value("${spring.application.name}")
    String appName;

    private Logger logger = LoggerFactory.getLogger("kafka");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String cookieVal=null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sid".equals(cookie.getName())) {
                    cookieVal = cookie.getValue();
                    break;
                }
            }
        }

        String rid = StringUtils.defaultIfBlank(req.getHeader("rid"), CommonUtils.getRandomStr(10));
        String tid = StringUtils.defaultIfBlank(req.getHeader("tid"), CommonUtils.getDevice(req.getHeader("User-Agent")));
        String sid = StringUtils.defaultIfBlank(req.getHeader("sid"), cookieVal);
        String ip = StringUtils.defaultIfBlank(req.getHeader("ip"), CommonUtils.getIpAddress(req));
        String url = "java:" + req.getRequestURI();
        LogBean logBean = new LogBean(rid, sid, tid, appName, "I am filter", ip, url);

        logger.info(logBean.toString());
        chain.doFilter(request, response);
    }
}

