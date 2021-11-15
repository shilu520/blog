package com.million.blog.dao.pojo;

import lombok.Data;

@Data
public class SysUser {

    //使用默认的分布式id 因为当用户多了的使用，会用到分表查询
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
