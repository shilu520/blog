package com.million.blog.controller;

import com.million.blog.service.ArticleService;
import com.million.blog.vo.Result;
import com.million.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页  文章列表
     * Result是统一结果返回
     */
    @PostMapping
    public Result articles (@RequestBody PageParams pageParams) {
        //ArticleVo 页面接收的数据
        return articleService.listArticlesPage(pageParams);
    }

    /**
     * 首页 最热文章
     * @return
     */
    @PostMapping("/hot")
    public Result hotArticles () {
        int limit = 5;
        return articleService.hotArticles(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @PostMapping("/new")
    public Result newArticles () {
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 首页 文章归档
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives () {
        return articleService.listArchives();
    }
}
