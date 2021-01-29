package com.wyt.lifecycle_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/1/29 17:11
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface AppLifeCycle {
}
