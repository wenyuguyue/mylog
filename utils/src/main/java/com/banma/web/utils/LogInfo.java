package com.banma.web.utils;

import javax.xml.bind.Element;
import java.lang.annotation.*;

/**
 * @author lichaofu
 * @create 2021-04-27 20:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface LogInfo {
    String value() default "";
}
