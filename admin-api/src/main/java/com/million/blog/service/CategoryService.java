package com.million.blog.service;

import com.million.blog.vo.CategoryVo;
import com.million.blog.vo.Result;

/**
 * @Author: studyboy
 * @Date: 2021/11/17  22:17
 */


public interface CategoryService {

    CategoryVo findCategoryById (Long categoryId);

    /**
     * 获取 文章分类信息
     * @return
     */
    Result categorys ();

    /**
     * 查看文件分类 详情
     * @return
     */
    Result categorysDetail ();

    /**
     * 把文章分类信息显示在文章的最上面
     * @param id
     * @return
     */
    Result categoryDetailById (Long id);
}
