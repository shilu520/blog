package com.million.blog.service;

import com.million.blog.dao.pojo.SysUser;
import com.million.blog.vo.Result;
import com.million.blog.vo.params.LoginParams;

/**
 * @Author: studyboy
 * @Date: 2021/11/13  21:39
 */

public interface LoginService {

    /**
     * 校验 token
     * @param token
     * @return
     */
    SysUser checkToken (String token);


    /**
     * 登录
     * @param loginParams
     * @return
     */
    Result login (LoginParams loginParams);

    Result logout (String token);

    /**
     * 注册
     * @param loginParams
     * @return
     */
    Result register (LoginParams loginParams);
}
