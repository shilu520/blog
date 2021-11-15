package com.million.blog.vo;

import com.million.blog.dao.pojo.SysUser;
import com.million.blog.dao.pojo.Tag;
import lombok.Data;

import java.util.List;

// * 和页面交互的数据最好和数据库的分开，减少耦合
@Data
public class ArticleVo {

    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

//    private ArticleBodyVo body;

    private List<TagVo> tags;

//    private List<CategoryVo> categorys;

}
