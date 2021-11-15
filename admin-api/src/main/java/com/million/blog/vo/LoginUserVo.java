package com.million.blog.vo;

import lombok.Data;

/**
 * @Author: studyboy
 * @Date: 2021/11/14  16:10
 */

@Data
public class LoginUserVo {

    private Long id;

    private String account;

    private String nickName;

    private String avatar;
}
