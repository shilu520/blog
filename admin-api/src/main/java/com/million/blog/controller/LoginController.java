package com.million.blog.controller;

import com.million.blog.service.LoginService;
import com.million.blog.vo.Result;
import com.million.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: studyboy
 * @Date: 2021/11/13  21:36
 */

@RestController
@RequestMapping("login")
public class LoginController {

    /**
     * 这是一个登录业务，SysUserService就用来查询用户表就行了
     * 可以创建一个新的登录service来解决登录业务
     */
    @Autowired
    private LoginService loginService;

    /**
     * 登录业务
     * @param loginParams
     * @return
     */
    @PostMapping
    public Result login(@RequestBody LoginParams loginParams){
        return loginService.login(loginParams);
    }
}
