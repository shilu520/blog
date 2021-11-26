package com.million.blog.service;

import com.million.blog.model.params.PageParam;
import com.million.blog.pojo.Permission;
import com.million.blog.vo.Result;

/**
 * @Author: studyboy
 * @Date: 2021/11/24  22:12
 */

public interface PermissionService {

    /**
     * 分页查询
     * @param pageParam
     * @return
     */
    Result listPermission (PageParam pageParam);

    /**
     * 添加
     * @param permission
     * @return
     */
    Result add (Permission permission);

    /**
     * 更新
     * @param permission
     * @return
     */
    Result update (Permission permission);

    /**
     * 删除
     * @param id
     * @return
     */
    Result delete (Long id);
}
