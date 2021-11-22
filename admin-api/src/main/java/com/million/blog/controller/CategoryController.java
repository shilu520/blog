package com.million.blog.controller;

import com.million.blog.service.CategoryService;
import com.million.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: studyboy
 * @Date: 2021/11/19  22:26
 */

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 写文章时 获取文章分类信息
     *
     * @return
     */
    @GetMapping
    public Result categorys () {
        return categoryService.categorys();
    }

    @GetMapping("/detail")
    public Result categorysDetail () {
        return categoryService.categorysDetail();
    }

    //接口url：/category/detail/{id}
    @GetMapping("/detail/{id}")
    public Result categoryDetailById (@PathVariable("id") Long id) {
        return categoryService.categoryDetailById(id);
    }
}
