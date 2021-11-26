package com.million.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: studyboy
 * @Date: 2021/11/25  11:05
 */

@Data
public class Admin {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

}
