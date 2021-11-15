package com.million.blog.service.impl;

import com.million.blog.dao.mapper.TagMapper;
import com.million.blog.dao.pojo.Tag;
import com.million.blog.service.TagsService;
import com.million.blog.vo.Result;
import com.million.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: studyboy
 * @Date: 2021/11/11  20:53
 */

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId (Long articleId) {
        //Mybatis-plus无法进行多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result hot (int limit) {

        /**
         *1.标签所拥有的文章最多，最热标签
         *2.查询 根据tag_id 分组 计数，从大到小， 排列，取前limit个
         */
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        //需求是tagId 和 tagName Tag对象
        //select tag_name tagName from ms_tag where id in (1,2,3,4)
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tagList);
    }
}
