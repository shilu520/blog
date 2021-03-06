package com.million.blog.controller;

import com.million.blog.service.TagsService;
import com.million.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: studyboy
 * @Date: 2021/11/13  9:29
 */

//表示返回的是json数据
@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    //  接口：/tags/hot
    @GetMapping("/hot")
    public Result hot(){
        int limit = 6;   //取最热标签前6个
        return tagsService.hot(limit);
    }
}
