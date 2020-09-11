package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Role;
import com.example.mapper.RoleMapper;
import com.example.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: project-demo
 * @Package: com.example.service.imp
 * @ClassName: NoticeServiceImpl
 * @Author: 游佳琪
 * @Description: Role类服务层接口实现类
 * @Date: 2020-8-18 20:39
 * @Version: 1.0
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    /**
     * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
     */
    @Override
    public List<Integer> queryRolePermissionIdsByRid(Integer roleId) {
        return this.baseMapper.queryRolePermissionIdsByRid(roleId);
    }

    /**
     * 保存角色和菜单权限之间的关系
     */
    @Override
    public void saveRolePermission(Integer rid, Integer[] ids) {
        RoleMapper roleMapper = this.getBaseMapper();
//        首先根据rid删除原本sys_role_permission中数据
        roleMapper.removeRolePermissionByRid(rid);
//        存入数据
        if (ids != null && ids.length > 0) {
            for (Integer pid : ids) {
                roleMapper.saveRolePermission(rid, pid);
            }
        }
    }

    /**
     * 查询当前用户拥有的角色ID集合
     */
    @Override
    public List<Integer> queryUserRoleIdsByUid(Integer id) {
        return this.getBaseMapper().queryUserRoleIdsByUid(id);
    }

    /**
     * 根据角色id删除角色及相关中间表中的信息
     */
    @Override
    public boolean removeById(Serializable id) {
//        在删除sys_role表中角色之前删除sys_role_permission和sys_role_user关联数据
//        1.根据角色id删除sys_role_permission表中对应的数据
        this.baseMapper.removeRolePermissionByRid(id);
//        2.根据角色id删除sys_role_user表中对应的数据
        this.baseMapper.removeRoleUserByRid(id);
        return super.removeById(id);
    }

}
