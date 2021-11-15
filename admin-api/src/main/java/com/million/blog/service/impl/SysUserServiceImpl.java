package com.million.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.million.blog.dao.mapper.SysUserMapper;
import com.million.blog.dao.pojo.SysUser;
import com.million.blog.service.LoginService;
import com.million.blog.service.SysUserService;
import com.million.blog.utils.JWTUtils;
import com.million.blog.vo.ErrorCode;
import com.million.blog.vo.LoginUserVo;
import com.million.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: studyboy
 * @Date: 2021/11/11  20:52
 */

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findSysUserById (Long authorId) {

        SysUser sysUser = sysUserMapper.selectById(authorId);
        //如果为空就设置一个默认的数值
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("百万年薪");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser (String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        //eq就是=，根据表中的数据和传入的数据进行比较
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getNickname);
        //加个limit 1 是为了加快查询数据，因为我们只需要查询1个，
        // 如果它查询不到就会一致查询，加上limit 1可以解决这个
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUsersByToken (String token) {

        /**
         * 1. token 合法性校验
         *   是否为空，解析是否成功  redis是否存在
         *  2. 如果校验失败  ，返回错误 可以在LoginService中校验
         *  3. 如果成功  返回对应的结果 LoginUserVo
         */
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(sysUser, loginUserVo);
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount (String account) {
        //创建条件构造器
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        //加上limit 1 可以加快查询速度
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save (SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }
}
