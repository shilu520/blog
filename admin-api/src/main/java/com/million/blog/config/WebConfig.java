package com.million.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: studyboy
 * @Date: 2021/11/9  11:37
 */

@Configuration
public class WebConfig  implements WebMvcConfigurer {

    @Override
    public void addCorsMappings (CorsRegistry registry) {

        //跨域配置，不可设置为*，不安全, 前后端分离项目，可能域名不一致
        //本地测试 端口不一致 也算跨域
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }
}

