package com.million.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.million.blog.dao.pojo.SysUser;
import com.million.blog.service.LoginService;
import com.million.blog.service.SysUserService;
import com.million.blog.utils.JWTUtils;
import com.million.blog.vo.ErrorCode;
import com.million.blog.vo.Result;
import com.million.blog.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: studyboy
 * @Date: 2021/11/13  21:39
 */
@Service
@Transactional   //添加数据库事务
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    private static final String slat = "mszlu!@#";

    @Override
    public SysUser checkToken (String token) {
        /**
         * 1. token 合法性校验
         *   是否为空，解析是否成功  redis是否存在  可以在LoginService中校验
         *  2. 如果校验失败  ，返回错误
         *  3. 如果成功  返回对应的结果 LoginUserVo
         */
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String usrJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (usrJson == null) {
            return null;
        }
        //这里把获得到的json转换成SysUser对象
        SysUser sysUser = JSON.parseObject(usrJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result login (LoginParams loginParams) {

        /**
         * 思路：
         * 1、检查参数是否合法
         * 2、根据用户名和密码去User表中查询 是否存在
         * 3、如果不存在，登录失败
         * 4、如果存在，使用jwt
         * 5、token放入redis当中，redis token： user信息，设置过期时间
         * （登录认证的时候，先认证token字符串是否合法，去redis认证是否存在）
         */

        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        //如果参数不合法，就返回错误的信息
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //使用md5加密
        password = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        //登录成功后，使用jwt生成token，返回到token和Redis中
        String token = JWTUtils.createToken(sysUser.getId());
        //传递Sysuser数据，需要转化成JSON的字符串，设置过期时间1天
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public Result logout (String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register (LoginParams loginParams) {

        /**
         * 思路：
         * 1.判断参数  是否合法
         * 2.判断账户是否存在， 若存在，返回账户已经被注册
         * 3.不存在，注册用户
         * 4.生成token
         * 5.存入redis并返回  需要把Sysuser对象转化成JSON字符串返回
         * 6.注意加上事务，一旦中间过程出现了错误，就需要立即回滚
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_ERROR.getCode(), ErrorCode.ACCOUNT_ERROR.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);

        //生成token
        String token = JWTUtils.createToken(sysUser.getId());
        //存入到redis中
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

}
