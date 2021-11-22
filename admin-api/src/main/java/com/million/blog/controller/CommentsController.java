package com.million.blog.controller;

import com.million.blog.service.CommentsService;
import com.million.blog.vo.Result;
import com.million.blog.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论列表
 *
 * @Author: studyboy
 * @Date: 2021/11/19  16:45
 */

@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    /**
     * 显示 评论列表
     *
     * @param articleId
     * @return
     */
    @GetMapping("/article/{id}")
    public Result comments (@PathVariable("id") Long articleId) {
        return commentsService.commentsByArticleId(articleId);
    }

    /**
     * 进行 评论功能
     *
     * @return
     */
    @PostMapping("/create/change")
    public Result comment (@RequestBody CommentParams commentParams) {
        return commentsService.comment(commentParams);
    }

}
