package com.million.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.million.blog.mapper.AdminMapper;
import com.million.blog.pojo.Admin;
import com.million.blog.pojo.Permission;
import com.million.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: studyboy
 * @Date: 2021/11/25  11:09
 */

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findAdminByUserName (String username) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        queryWrapper.last("limit 1"); //保证查询出现的结果是一个
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public List<Permission> findPermissionByAdminId (Long adminId) {
        //SELECT * FROM `ms_permission` where id in (select permission_id from ms_admin_permission where admin_id = 1)
        return adminMapper.findPermissionByAdminId(adminId);
    }
}
