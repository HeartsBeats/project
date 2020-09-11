package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Permission;
import com.example.mapper.PermissionMapper;
import com.example.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @ProjectName: project-demo
 * @Package: com.example.service.imp
 * @ClassName: PermissionServiceImpl
 * @Author: 游佳琪
 * @Description: PermissionService接口实现类
 * @Date: 2020-8-14 10:57
 * @Version: 1.0
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public boolean removeById(Serializable id) {
//        <!-- /根据权限或菜单ID删除权限表各和角色的关系表里面的数据 -->
        PermissionMapper permissionMapper = this.baseMapper;
        permissionMapper.deleteRolePermissionByPid(id);
        return super.removeById(id);
    }
}
