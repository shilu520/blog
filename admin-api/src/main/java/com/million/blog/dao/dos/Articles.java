package com.million.blog.dao.dos;

import lombok.Data;

/**
 * @Author: studyboy
 * @Date: 2021/11/13  17:21
 * 再dos下的数据不是持久化的
 */
@Data
public class Articles {


    private Integer year;

    private Integer month;

    /**
     *文章数量
     */
    private Long count;
}
