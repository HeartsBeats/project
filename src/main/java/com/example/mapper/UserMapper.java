package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * @ProjectName: project-demo
 * @Package: com.example.mapper
 * @ClassName: userMapper
 * @Author: 游佳琪
 * @Description: Mapper接口
 * @Date: 2020-8-12 22:14
 * @Version: 1.0
 */
public interface UserMapper extends BaseMapper<User> {


    /**
     * 根据用户ID删除用户角色中间表的数据
     *
     * @param id
     */
    void deleteRoleUserByUid(@Param("id") Serializable id);

    /**
     * 保存角色和用户的关系
     *
     * @param uid
     * @param rid
     */
    void insertUserRole(@Param("uid") Integer uid, @Param("rid") Integer rid);

}

