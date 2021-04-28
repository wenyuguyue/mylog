package com.banma.mylog.user;

import com.alibaba.nacos.client.naming.utils.RandomUtils;
import com.banma.web.utils.LogBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@SpringBootApplication
@RefreshScope
@RestController
@EnableDiscoveryClient
@ComponentScan({"com.banma.web.utils","com.banma.mylog"})
public class UserApplication {
    Logger logger = LoggerFactory.getLogger("kafka");

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @RequestMapping("/test")
    public Object test() {
        LogBean logBean = new LogBean(new Random().nextInt(9000) + "", "user", "pc", "java:user", "user test required", "ip", "url");
        logger.info(logBean.toString());
        return logBean;
    }
}
