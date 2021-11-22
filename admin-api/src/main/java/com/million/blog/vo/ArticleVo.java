package com.million.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

// * 和页面交互的数据最好和数据库的分开，减少耦合
@Data
public class ArticleVo {

    /**
     * 有些文章id是雪花算法生产的19位数字。初始查询返回的json数据
     * 中id为19位，而jsNumber类型最多16位，超出的位数不保证精度，
     * 导致前端再次查询文章时的请求参数id出错
     * 可在对应的Vo类的id字段添加如下注解
     * json序列化时转成字符串的形式
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private ArticleBodyVo body;

    private List<TagVo> tags;
    /**
     *  文章分类 理应只能有一个分类 多个标签
     */
    private CategoryVo category;

}
