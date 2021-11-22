package com.million.blog.dao.pojo;

import lombok.Data;

/**
 * @Author: studyboy
 * @Date: 2021/11/19  17:00
 */

@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    /**
     * 如果为1表示有子评论
     */
    private Integer level;
}
