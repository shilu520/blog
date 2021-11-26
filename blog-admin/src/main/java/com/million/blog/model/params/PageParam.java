package com.million.blog.model.params;

import lombok.Data;

/**
 * @Author: studyboy
 * @Date: 2021/11/24  22:11
 */

@Data
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
