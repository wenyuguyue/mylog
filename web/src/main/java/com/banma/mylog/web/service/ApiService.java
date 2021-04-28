package com.banma.mylog.web.service;

import com.banma.web.utils.LogBean;
import com.banma.web.utils.LogInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author lichaofu
 * @create 2021-04-27 20:00
 */
@Service
public class ApiService {
    @Autowired
    RestTemplate restTemplate;

    private static Logger logger = LoggerFactory.getLogger("kafka");

    @LogInfo
    public Object test() {
        LogBean logBean = LogBean.logBeanThreadLocal.get();
        logBean.setMessage("this is service");
        logger.info(logBean.toString());

        Object res = restTemplate.getForObject("http://localhost:8003/user/info", Object.class);
        logBean.setMessage("after call user,res= " + res);
        logger.info(logBean.toString());
        return res;
    }

    @LogInfo
    public boolean login(String username, String password) {
        Boolean res = restTemplate.getForObject("http://localhost:8003/user/check?username=" + username + "&password=" + password, Boolean.class);
        return res;
    }
}
