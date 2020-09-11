package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: project-demo
 * @Package: com.example.mapper
 * @ClassName: DeptMapper
 * @Author: 游佳琪
 * @Description: Role实体类的mapper接口
 * @Date: 2020-8-21 09:04
 * @Version: 1.0
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
     */
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    /**
     * 保存角色和菜单权限之间的关系
     */
    void saveRolePermission(@Param("rid") Integer rid, @Param("pid") Integer pid);

    /**
     * 根据角色id删除sys_role_permission表中对应的数据
     */
    void removeRolePermissionByRid(Serializable id);

    /**
     * 根据角色id删除sys_role_user表中对应的数据
     */
    void removeRoleUserByRid(Serializable id);

    /**
     * 查询当前用户拥有的角色ID集合
     */
    List<Integer> queryUserRoleIdsByUid(Integer id);

}
