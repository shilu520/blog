package com.million.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.million.blog.pojo.Admin;
import com.million.blog.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: studyboy
 * @Date: 2021/11/25  11:10
 */

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("SELECT * FROM ms_permission where id in (select permission_id from ms_admin_permission where admin_id =#{adminId})")
    List<Permission> findPermissionByAdminId (Long adminId);
}
