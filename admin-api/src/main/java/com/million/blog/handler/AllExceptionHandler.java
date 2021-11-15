package com.million.blog.handler;

import com.million.blog.vo.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: studyboy
 * @Date: 2021/11/13  10:49
 */

//这个注解的意思是对所有加了Controller注解的类进行拦截处理Aop的实现
@ControllerAdvice
public class AllExceptionHandler {

    //进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody //返回json数据到页面上
    public Result doException(Exception e){
        e.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
