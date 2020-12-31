package com.wind.androidlearn.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: LL
 * @Description: APT的注解
 * @Date:Create：in 2020/12/31 16:54
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Route {
    String value() default "";
}
