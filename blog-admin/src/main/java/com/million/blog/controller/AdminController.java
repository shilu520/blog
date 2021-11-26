package com.million.blog.controller;

import com.million.blog.model.params.PageParam;
import com.million.blog.pojo.Permission;
import com.million.blog.pojo.SysUser;
import com.million.blog.service.PermissionService;
import com.million.blog.service.SysUserService;
import com.million.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: studyboy
 * @Date: 2021/11/24  22:01
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 权限管理
     */
    @PostMapping("permission/permissionList")
    public Result listPermission (@RequestBody PageParam pageParam) {

        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result add (@RequestBody Permission permission) {
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update (@RequestBody Permission permission) {
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete (@PathVariable Long id) {
        return permissionService.delete(id);
    }

    /**
     * 用户管理
     */
    @PostMapping("user/adminList")
    public Result listSysUser(@RequestBody PageParam pageParam){
        return sysUserService.listSysUser(pageParam);
    }

    @PostMapping("user/update")
    public Result update (@RequestBody SysUser sysUser) {
        return sysUserService.update(sysUser);
    }

    @GetMapping("user/delete/{id}")
    public Result deleteById (@PathVariable Long id) {
        return sysUserService.deleteById(id);
    }

}
