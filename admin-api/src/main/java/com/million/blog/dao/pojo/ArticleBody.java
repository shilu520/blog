package com.million.blog.dao.pojo;

import lombok.Data;

/**
 * @Author: studyboy
 * @Date: 2021/11/17  21:38
 */

@Data
public class ArticleBody {

    private Long id;

    private String content;

    private String contentHtml;

    private Long articleId;
}
