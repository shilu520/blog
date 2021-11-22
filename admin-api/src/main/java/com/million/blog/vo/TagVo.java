package com.million.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: studyboy
 * @Date: 2021/11/11  20:32
 */

@Data
public class TagVo{

    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String tagName;

    private String avatar;


}
