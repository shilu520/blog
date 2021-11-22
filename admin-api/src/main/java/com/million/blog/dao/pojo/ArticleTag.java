package com.million.blog.dao.pojo;

import lombok.Data;

/**
 * @Author: studyboy
 * @Date: 2021/11/20  16:51
 */

@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}
