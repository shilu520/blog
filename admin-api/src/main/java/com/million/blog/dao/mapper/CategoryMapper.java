package com.million.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.million.blog.dao.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: studyboy
 * @Date: 2021/11/17  22:17
 */

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
