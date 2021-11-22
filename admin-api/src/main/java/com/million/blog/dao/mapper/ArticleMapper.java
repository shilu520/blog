package com.million.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.million.blog.dao.dos.Articles;
import com.million.blog.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: studyboy
 * @Date: 2021/11/11  20:18
 */

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 首页 文章归档
     *
     * @return
     */
    List<Articles> listArchives ();

    /**
     * 自定义文章归档，根据年份和月来查询
     *
     * @param page
     * @param categoryId
     * @param tagId
     * @param year
     * @param month
     * @return
     */
    IPage<Article> listArticlesPage (Page<Article> page,
                                     Long categoryId,
                                     Long tagId,
                                     String year,
                                     String month);

}
