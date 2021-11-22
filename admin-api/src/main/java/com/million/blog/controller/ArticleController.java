package com.million.blog.controller;

import com.million.blog.common.aop.LogAnnotation;
import com.million.blog.common.cache.Cache;
import com.million.blog.service.ArticleService;
import com.million.blog.vo.Result;
import com.million.blog.vo.params.ArticleParam;
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
    //自定义AOP日志
    @LogAnnotation(module="文章",operator="获取文章列表")
    @Cache(expire = 5 * 60 *1000,name="listArticlesPage")
    @PostMapping
    public Result articles (@RequestBody PageParams pageParams) {
        //ArticleVo 页面接收的数据
        return articleService.listArticlesPage(pageParams);
    }

    /**
     * 首页 最热文章
     *
     * @return
     */
    @Cache(expire = 5 * 60 *1000,name="hot_Article")
    @PostMapping("/hot")
    public Result hotArticles () {
        int limit = 5;
        return articleService.hotArticles(limit);
    }

    /**
     * 首页 最新文章
     *
     * @return
     */
    @Cache(expire = 5 * 60 *1000,name="news_Article")
    @PostMapping("/new")
    public Result newArticles () {
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 首页 文章归档
     *
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives () {
        return articleService.listArchives();
    }

    /**
     * 文章信息
     *
     * @param articleId
     * @return
     */
    @PostMapping("/view/{id}")
    public Result findArticleById (@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    /**
     * 文章 发布服务
     * 接口url：/articles/publish
     * 请求方式：POST
     * @param articleParam
     * @return
     */
    @PostMapping("/publish")
    public Result publish (@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }
}
