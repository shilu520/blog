package com.million.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class UserVo {

    private String nickname;

    private String avatar;

    /**
     * 注意：
     * commentVo 中引用的usrVo的ID也要注意精度损失的问题，
     * 否则在前端展示二级评论toUser时只会显示默认设置的用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
}