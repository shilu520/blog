package com.million.blog.vo.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.million.blog.vo.CategoryVo;
import com.million.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @Author: studyboy
 * @Date: 2021/11/20  16:33
 */

@Data
public class ArticleParam {

    /**
     * 文章id（编辑有值) 发布的时候没有id
     */
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private ArticleBodyParam body;

    /**
     * 文章分类参数
     */
    private CategoryVo category;

    private String summary;

    /**
     * 文章标签参数
     */
    private List<TagVo> tags;

    private String title;
}
