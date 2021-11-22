package com.million.blog.handler;

import com.alibaba.fastjson.JSON;
import com.million.blog.dao.pojo.SysUser;
import com.million.blog.service.LoginService;
import com.million.blog.utils.UserThreadLocal;
import com.million.blog.vo.ErrorCode;
import com.million.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author: studyboy
 * @Date: 2021/11/16  1:04
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;


    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * 在执行controller（方法时判断）Handler之前进行执行
         * 1. 需要判断 请求的接口路径  是否为HandlerMethod（controller 方法）
         * 2、token是否存在  如果为空  未登录
         * 3、token 不为空  就登录认证 loginService checkToken
         * 4.如果认证成功  放行即可
         */

        //instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例
        if (!(handler instanceof HandlerMethod)) {
            //既然请求的不是controller中的方法，那么就不需要拦截了
            return true;
        }
        //通过请求头获取token
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}", requestURI);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");
        if (StringUtils.isBlank(token)) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            //我们需要设置返回的数据是JSON数据
            response.setContentType("application/json;charset=utf-8");
            //需要返回JSON对象给前端
            response.getWriter().write(JSON.toJSONString(response));
            return false;
        }
        //登录认证
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            //我们需要设置返回的数据是JSON数据
            response.setContentType("application/json;charset=utf-8");
            //需要返回JSON对象给前端
            response.getWriter().write(JSON.toJSONString(result));
            return false;
        }
        //认证成功，放行
        //我们希望在controller 中直接获取用户的信息  怎么获取？
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //在渲染数据之后就要删除这个线程
        //如果不删除ThreadLocal中用完的信息，会有内存泄漏的风险
        UserThreadLocal.remove();

    }
}
