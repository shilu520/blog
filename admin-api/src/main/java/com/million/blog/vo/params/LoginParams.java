package com.million.blog.vo.params;

import lombok.Data;

/**
 * @Author: studyboy
 * @Date: 2021/11/13  21:38
 */

@Data
public class LoginParams {

    private String account;

    private String password;

    private String nickname;
}
