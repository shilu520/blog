package com.million.blog.dao.pojo;

import lombok.Data;

/**
 * @Author: studyboy
 * @Date: 2021/11/17  21:40
 */

@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
