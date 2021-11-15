package com.million.blog.service;

import com.million.blog.dao.pojo.SysUser;
import com.million.blog.vo.Result;

/**
 * @Author: studyboy
 * @Date: 2021/11/11  20:36
 */
public interface SysUserService {

    /**
     * 查询作者id
     * @param authorId
     * @return
     */
    SysUser findSysUserById (Long authorId);

    /**
     * 查找user表中的用户
     * @param account
     * @param password
     * @return
     */
    SysUser findUser (String account, String password);

    /**
     * 根据 token查询用户信息
     * @param token
     * @return
     */
    Result findUsersByToken (String token);

    /**
     * 查询账户
     * @param account
     * @return
     */
    SysUser findUserByAccount (String account);

    /**
     * 保存用户 ，mybatis_plus采用雪花算法
     * @param sysUser
     */
    void save (SysUser sysUser);
}
