package com.million.blog.common.cache;

import java.lang.annotation.*;

/**
 * @Author: studyboy
 * @Date: 2021/11/22  19:47
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    /**
     * 过期时间
     * @return
     */
    long expire () default 60 * 1000;

    /**
     * 缓存表示key
     * @return
     */
    String name () default "";
}
