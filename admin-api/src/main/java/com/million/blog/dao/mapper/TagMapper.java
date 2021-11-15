package com.million.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.million.blog.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id 查询标签
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(@Param("articleId") Long articleId);

    /**
     * 查询最热标签id
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds (int limit);

    /**
     * 通过最热标签来查询对应的标签名字
     * @param tagIds
     * @return
     */
    List<Tag> findTagsByTagIds (List<Long> tagIds);
}
