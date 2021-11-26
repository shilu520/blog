package com.million.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.million.blog.mapper.PermissionMapper;
import com.million.blog.model.params.PageParam;
import com.million.blog.pojo.Permission;
import com.million.blog.service.PermissionService;
import com.million.blog.vo.PageResult;
import com.million.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: studyboy
 * @Date: 2021/11/24  22:12
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Result listPermission (PageParam pageParam) {

        /**
         * 要的数据，管理台  表的所以的字段 Permission
         */
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(pageParam.getQueryString())) {
            queryWrapper.eq(Permission::getName, pageParam.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, queryWrapper);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }

    @Override
    public Result add (@RequestBody  Permission permission) {
        permissionMapper.insert(permission);
        return Result.success(null);
    }

    @Override
    public Result update (Permission permission) {
        permissionMapper.updateById(permission);
        return Result.success(null);
    }

    @Override
    public Result delete (Long id) {
        permissionMapper.deleteById(id);
        return Result.success(null);
    }


}
