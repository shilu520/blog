package com.million.blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    //页面需要list和total
    private List<T> list;

    private Long total;
}
