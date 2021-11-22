package com.million.blog.service.impl;

import com.million.blog.dao.mapper.ArticleBodyMapper;
import com.million.blog.dao.pojo.ArticleBody;
import com.million.blog.service.ArticleBodyService;
import com.million.blog.vo.ArticleBodyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: studyboy
 * @Date: 2021/11/17  22:04
 */
@Service
public class ArticleBodyServiceImpl implements ArticleBodyService {

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public ArticleBodyVo findArticleBodyById (Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        //将数据库中的articleBody的content数据赋值给articleBodyVo对象中的content
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
