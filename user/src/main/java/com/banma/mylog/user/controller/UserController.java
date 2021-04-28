package com.banma.mylog.user.controller;

import com.banma.web.utils.LogBean;
import com.banma.web.utils.LogInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lichaofu
 * @create 2021-04-27 20:46
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger("kafka");

    @RequestMapping("info")
    @LogInfo
    public Object info() {
        LogBean logBean = LogBean.logBeanThreadLocal.get();
        logBean.setMessage("this is user.UserController");
        logger.info(logBean.toString());
        return "\"sb\":\"menxin\"";
    }

    @LogInfo
    @RequestMapping("/check")
    public boolean check(String username, String password) {
        LogBean logBean = LogBean.logBeanThreadLocal.get();
        logBean.setMessage("correct user");
        logger.info(logBean.toString());

        return true;
    }
}
