package com.banma.mylog.web.controller;

import com.banma.mylog.web.service.ApiService;
import com.banma.web.utils.LogBean;
import com.banma.web.utils.LogInfo;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lichaofu
 * @create 2021-04-27 18:43
 */
@RequestMapping("/api")
@RestController
public class ApiController {
    private static Logger logger = LoggerFactory.getLogger("kafka");

    @Autowired
    ApiService apiService;

    @RequestMapping("/test")
    @LogInfo
    public Object test() {
        LogBean logBean = LogBean.logBeanThreadLocal.get();
        logBean.setMessage("this is controller");
        logger.info(logBean.toString());
        return apiService.test();
    }

    @RequestMapping("/login")
    @LogInfo
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean ok = apiService.login(username, password);

        if (ok) {
            Cookie cookie = new Cookie("sid", username);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "success";
        }
        return "fail";
    }
}
