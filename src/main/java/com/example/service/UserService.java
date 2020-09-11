package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.User;

/**
 * @ProjectName: project-demo
 * @Package: com.example.service.imp
 * @ClassName: userService
 * @Author: 游佳琪
 * @Description:
 * @Date: 2020-8-12 22:20
 * @Version: 1.0
 */
public interface UserService extends IService<User> {

    /**
     * 保存用户和角色之间的关系
     * @param uid
     * @param ids
     */
    void saveUserRole(Integer uid, Integer[] ids);
}

