package com.million.blog.vo.params;

import lombok.Data;

/**
 * @Author: studyboy
 * @Date: 2021/11/11  20:32
 */

@Data
public class PageParams {

    private int page = 1;

    private int pageSize  = 10;
}
