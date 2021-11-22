package com.million.blog.service;

import com.million.blog.vo.Result;
import com.million.blog.vo.TagVo;

import java.util.List;

/**
 * @Author: studyboy
 * @Date: 2021/11/11  20:36
 */
public interface TagsService {

    /**
     * 通过文章id 查询标签
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId (Long articleId);

    /**
     * 最热标签
     * @param limit
     * @return
     */
    Result hot (int limit);

    /**
     * 写文章时获取标签
     * @return
     */
    Result findAll ();

    /**
     * 文章分类中的标签分类
     * @return
     */
    Result tagsDetail ();

    /**
     *通过标签id  来查看有哪些文章
     * @param id
     * @return
     */
    Result tagsDetailById (Long id);
}
