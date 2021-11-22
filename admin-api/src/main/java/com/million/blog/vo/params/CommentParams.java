package com.million.blog.vo.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Author: studyboy
 * @Date: 2021/11/19  21:01
 */

@Data
public class CommentParams {

    /**
     * 文章id
     */
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论id
     */
    private Long parent;

    /**
     * 被评论的用户id
     */
    private Long toUserId;
}
