package com.banma.web.utils;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lichaofu
 * @create 2021-04-27 10:08
 */
@Data
@NoArgsConstructor
public class LogBean {
    private String rid, sid, tid, from, message, ip, url;
    private Long time;

    public static ThreadLocal<LogBean> logBeanThreadLocal = new ThreadLocal<>();

    @Override
    public String toString() {
        this.setTime(System.currentTimeMillis());
        return JSON.toJSONString(this);
    }

    public LogBean(String rid, String sid, String tid, String from, String message, String ip, String url) {
        this.rid = rid;
        this.sid = sid;
        this.tid = tid;
        this.from = from;
        this.message = message;
        this.ip = ip;
        this.url = url;
        logBeanThreadLocal.set(this);
    }
}
