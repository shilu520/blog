package com.million.blog.config;

import com.million.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: studyboy
 * @Date: 2021/11/9  11:37
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings (CorsRegistry registry) {

        //跨域配置，不可设置为*，不安全, 前后端分离项目，可能域名不一致
        //本地测试 端口不一致 也算跨域
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        //拦截test接口。后续实际遇到需要拦截的接口时，在配置成真正的拦截接口
        //将评论功能添加到登录拦截器这里
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");
    }
}

