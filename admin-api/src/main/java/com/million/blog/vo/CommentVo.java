package com.million.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @Author: studyboy
 * @Date: 2021/11/19  17:51
 */

@Data
public class CommentVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 注意：
     * commentVo 中引用的usrVo的ID也要注意精度损失的问题，
     * 否则在前端展示二级评论toUser时只会显示默认设置的用户
     */
    private UserVo author;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    private UserVo toUser;
}
