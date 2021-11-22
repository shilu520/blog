package com.million.blog.utils;

import com.million.blog.dao.pojo.SysUser;
import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;

/**
 * @Author: studyboy
 * @Date: 2021/11/17  20:12
 */
public class UserThreadLocal {

    //私有的，不能让别人用
    private UserThreadLocal () {
    }

    public static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put (SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get () {
        return LOCAL.get();
    }

    public static void remove () {
        LOCAL.remove();
    }
}
