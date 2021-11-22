package com.million.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.million.blog.dao.mapper.ArticleMapper;
import com.million.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: studyboy
 * @Date: 2021/11/18  20:22
 */

@Component
public class ThreadService {


    /**
     * 期望此操作在线程池李 执行 不会影响原有的主线程
     *
     * @param articleMapper
     * @param article
     */
    @Async("taskExecutor")  //告诉ThreadPoolConfig
    public void updateArticleViewCount (ArticleMapper articleMapper, Article article) {

        //原有的，就是页面上显示的
        int viewCounts = article.getViewCounts();
        //为了最小限度的修改，我们这里需要自己new一个
        Article updateArticle = new Article();
        updateArticle.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, article.getId());
        //设置一个 为了在多线程的环境下，线程安全
        /**
         *为什么要多加一个view_count呢？
         * 因为当在执行更新操作的时候，如果有10个用户同时访问一篇文章，那么数据库的阅读量
         * 就增加了10条，此时如果不判断当前的阅读量和数据库的阅读量是否一致时，
         * 就会出现只通过了id判断增加了一次阅读量，而那10次阅读量就会飞走。
         * 但是如果真的有10个人的话，那么这个view_count是不相等的，那么update就会执行失败，
         * 数据会停留在110 ，而不是111。需要解决这个bug，可以用redis
         */
        updateWrapper.eq(Article::getViewCounts, viewCounts);
        //update article set view_count=100 where view _count=99 and id = 11;
        articleMapper.update(updateArticle, updateWrapper);
    }
}
