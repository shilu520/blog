package com.million.blog.service;

import com.million.blog.vo.Result;
import com.million.blog.vo.TagVo;

import java.util.List;

/**
 * @Author: studyboy
 * @Date: 2021/11/11  20:36
 */
public interface TagsService {
    List<TagVo> findTagsByArticleId (Long articleId);

    Result hot (int limit);
}
