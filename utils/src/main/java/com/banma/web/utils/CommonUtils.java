package com.banma.web.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author lichaofu
 * @create 2021-04-27 19:31
 */
public class CommonUtils {
    public static String getRandomStr(int len) {
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = chars[new Random().nextInt(61)];
        }
        return new String(str);
    }

    public static String getDevice(String userAgent) {
        String[] terminals = {
                "iphone", "android", "touch", "ipad", "symbian", "htc", "palmos", "blackberry", "operamini", "windows ce", "nokia", "fennec", "macintosh", "hiptop", "kindle", "mot"
        };
        userAgent = userAgent.toLowerCase();
        for (String terminal : terminals) {
            if (userAgent.indexOf(terminal) != -1) {
                return terminal;
            }
        }
        return userAgent;
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_Client_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
