package com.million.blog.service;

import com.million.blog.vo.Result;
import com.million.blog.vo.ArticleVo;
import com.million.blog.vo.params.ArticleParam;
import com.million.blog.vo.params.PageParams;

import java.util.List;

public interface ArticleService {

    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */

    Result listArticlesPage(PageParams pageParams);

    /**
     * 首页 最热文章
     * @param limit
     * @return
     */
    Result hotArticles (int limit);

    /**
     * 首页 最新文章
     * @param limit
     * @return
     */
    Result newArticles (int limit);

    /**
     * 首页 文章归档
     * @return
     */
    Result listArchives ();

    /**
     * 文章详情
     * @param articleId
     * @return
     */
    Result findArticleById (Long articleId);

    /**
     * 文章发布服务
     * @param articleParam
     * @return
     */
    Result publish (ArticleParam articleParam);
}
