package com.million.blog.service;

import com.million.blog.vo.Result;
import com.million.blog.vo.params.CommentParams;

/**
 * @Author: studyboy
 * @Date: 2021/11/19  16:50
 */

public interface CommentsService {

    /**
     * 根据文章id   查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId (Long id);

    /**
     * 进行 评论功能
     * @param commentParams
     * @return
     */
    Result comment (CommentParams commentParams);
}
