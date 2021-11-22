package com.million.blog.controller;

import com.million.blog.utils.QiniuUtils;
import com.million.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @Author: studyboy
 * @Date: 2021/11/20  21:18
 */

@RestController
@RequestMapping("upload")
public class uploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload (@RequestParam("image") MultipartFile file) {
        //源文件名称 比如aa.png
        String originalFilename = file.getOriginalFilename();
        //获取唯一的文件名称
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        //上传的文件  上传到哪呢？  七牛云
        //降低我们自身应用服务器的带宽消耗
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
            return Result.success(QiniuUtils.url + fileName);
        }
        return Result.fail(20001,"上传失败");
    }
}
