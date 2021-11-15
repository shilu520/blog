package com.million.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
    List<Articles> listArchives ();
}
