package com.million.blog.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: studyboy
 * @Date: 2021/11/20  20:43
 */
public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest () {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
