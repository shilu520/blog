package com.million.blog.service;

import com.million.blog.pojo.Admin;
import com.million.blog.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: studyboy
 * @Date: 2021/11/25  11:26
 */

@Service
public class AuthService {

    @Autowired
    private AdminService adminService;

    public boolean auth (HttpServletRequest request, Authentication authentication) {

        /**
         *  权限认证思路：
         * 1、先得到请求的路径
         * 2、获取userDetails的信息  判断是否为空  空 返回false
         * 非空principal才是userDetails  得到用户名  通过用户名得到admin
         * 通过admin得到id  通过ms_admin_permission 关联表来查询Permission
         * 2、通过路径比较
         */
        //请求路径
        String requestURI = request.getRequestURI();
        //拿到当前的登录用户信息
        Object principal = authentication.getPrincipal();
        //需要判断一下是否为空或者是匿名用户
        if (principal == null || "anonymousUser".equals(principal)) {
            //未登录
            return false;
        }
        //通过才说principal是userDetails
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUserName(username);
        if(admin.getId() == 1){
            //admin的id为1的话就是超级管理员 直接放行
            return true;
        }
        Long id = admin.getId();
        //通过ms_admin_permission 关联表来查询Permission
        List<Permission> permissionList = adminService.findPermissionByAdminId(id);
        //考虑到传的可能有?的存在，需要去掉  就取?前面的那一段
        requestURI = StringUtils.split(requestURI, "?")[0];
        for (Permission permission : permissionList) {
            if (requestURI.equals(permission.getPath())) {
                return true;
            }
        }
        return false;
    }
}
