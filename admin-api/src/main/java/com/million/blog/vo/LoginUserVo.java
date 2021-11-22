package com.million.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Author: studyboy
 * @Date: 2021/11/14  16:10
 */

@Data
public class LoginUserVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String account;

    private String nickName;

    private String avatar;
}
