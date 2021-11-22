package com.million.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.million.blog.dao.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: studyboy
 * @Date: 2021/11/19  16:52
 */

@Mapper
public interface CommentsMapper extends BaseMapper<Comment> {
}
