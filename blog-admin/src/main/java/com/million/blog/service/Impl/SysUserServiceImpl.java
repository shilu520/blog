package com.million.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.million.blog.mapper.SysUserMapper;
import com.million.blog.model.params.PageParam;
import com.million.blog.pojo.SysUser;
import com.million.blog.service.SysUserService;
import com.million.blog.vo.PageResult;
import com.million.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: studyboy
 * @Date: 2021/11/26  13:27
 */

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Result listSysUser (PageParam pageParam) {
        Page<SysUser> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(pageParam.getQueryString())) {
            queryWrapper.eq(SysUser::getNickname, pageParam.getQueryString());
        }
        Page<SysUser> sysUserPage = sysUserMapper.selectPage(page, queryWrapper);
        PageResult<SysUser> pageResult = new PageResult<>();
        pageResult.setList(sysUserPage.getRecords());
        pageResult.setTotal(sysUserPage.getTotal());
        return Result.success(pageResult);
    }

    @Override
    public Result update (SysUser sysUser) {
        sysUserMapper.updateById(sysUser);
        return Result.success(null);
    }

    @Override
    public Result deleteById (Long id) {
        sysUserMapper.deleteById(id);
        return Result.success(null);
    }
}
