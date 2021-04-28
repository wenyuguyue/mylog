package com.banma.web.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author lichaofu
 * @create 2021-04-27 20:16
 */
@Aspect
@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger("kafka");

    @Pointcut("@annotation(com.banma.web.utils.LogInfo)")
    public void log() {
    }

    @Around("log()")
    public Object around(ProceedingJoinPoint point) {
        try {
            MethodSignature signature = (MethodSignature) point.getSignature();
            String className = point.getTarget().getClass().getSimpleName();
            String methodName = signature.getName();

            LogBean logBean = LogBean.logBeanThreadLocal.get();
            logBean.setMessage("before " + className + "." + methodName);
            logger.info(logBean.toString());


            Object o = point.proceed();

            logBean.setMessage("after " + className + "." + methodName);
            logger.info(logBean.toString());

            return o;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
