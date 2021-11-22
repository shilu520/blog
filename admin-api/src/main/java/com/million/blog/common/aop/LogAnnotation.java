package com.million.blog.common.aop;

import java.lang.annotation.*;

/**
 * 日志注解
 * @Author: studyboy
 * @Date: 2021/11/20  20:47
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operator() default "";
}
