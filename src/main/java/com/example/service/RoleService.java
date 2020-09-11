package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Role;

import java.util.List;

/**
 * @ProjectName: project-demo
 * @Package: com.example.service
 * @ClassName: DeptService
 * @Author: 游佳琪
 * @Description: Role的service接口
 * @Date: 2020-8-21 09:06
 * @Version: 1.0
 */
public interface RoleService extends IService<Role> {

    /**
     * 功能描述: <br>   根据角色ID查询当前角色拥有的所有的权限或菜单ID
     * 〈〉
     *
     * @Param: [roleId]
     * @Return: java.util.List<java.lang.Integer>
     * @Author: YJQ
     * @Date: 2020-8-26 08:37
     */
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    /**
     * 保存角色和菜单权限之间的关系
     */
    void saveRolePermission(Integer rid, Integer[] ids);

    /**
     * 查询当前用户拥有的角色ID集合
     *
     * @param id
     * @return
     */
    List<Integer> queryUserRoleIdsByUid(Integer id);
}
