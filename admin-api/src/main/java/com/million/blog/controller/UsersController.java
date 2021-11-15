package com.million.blog.controller;

import com.million.blog.service.SysUserService;
import com.million.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: studyboy
 * @Date: 2021/11/14  15:36
 */

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private SysUserService sysUserService;

    //参数是一个请求头
    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUsersByToken(token);
    }
}
