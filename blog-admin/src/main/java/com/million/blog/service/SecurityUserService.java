package com.million.blog.service;

import com.million.blog.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author: studyboy
 * @Date: 2021/11/25  11:13
 */

@Component
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        //登录的时候，会把username  传递到这里
        //用过username 查询 admin表  如果admin存在 将密码告诉给spring security
        //如果不存在  返回null  认证失败了
        Admin admin = adminService.findAdminByUserName(username);
        if (admin == null) {
            //认证失败
            return null;
        }
        //这第三个参数先执为空  Collection<? extends GrantedAuthority> authorities
        UserDetails userDetails = new User(username, admin.getPassword(), new ArrayList<>());
        return userDetails;
    }
}
