package com.million.blog.controller;

import com.million.blog.service.LoginService;
import com.million.blog.vo.Result;
import com.million.blog.vo.params.LoginParams;
import com.million.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: studyboy
 * @Date: 2021/11/14  17:39
 */

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParams loginParams){
        return loginService.register(loginParams);
    }
}
