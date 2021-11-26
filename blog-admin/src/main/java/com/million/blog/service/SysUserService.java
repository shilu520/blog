package com.million.blog.service;

import com.million.blog.model.params.PageParam;
import com.million.blog.pojo.SysUser;
import com.million.blog.vo.Result;

/**
 * @Author: studyboy
 * @Date: 2021/11/26  13:24
 */

public interface SysUserService {
    Result listSysUser (PageParam pageParam);

    Result update (SysUser sysUser);

    Result deleteById (Long id);
}
