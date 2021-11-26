package com.million.blog.service;

import com.million.blog.pojo.Admin;
import com.million.blog.pojo.Permission;

import java.util.List;

/**
 * @Author: studyboy
 * @Date: 2021/11/25  11:04
 */
public interface AdminService {

    /**
     * 通过用户名查询Admin对象
     * @param username
     * @return
     */
    public Admin findAdminByUserName(String username);

    /**
     * 通过admin 的id  关系查询到Permission
     * @param id
     * @return
     */
    List<Permission> findPermissionByAdminId (Long id);
}
