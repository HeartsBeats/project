package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * @ProjectName: project-demo
 * @Package: com.example.mapper
 * @ClassName: PermissionMapper
 * @Author: 游佳琪
 * @Description: PermissionMapper接口
 * @Date: 2020-8-14 10:55
 * @Version: 1.0
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    void deleteRolePermissionByPid(@Param("id") Serializable id);
}
