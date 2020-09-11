package com.example.common;

import com.example.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ProjectName: project-demo
 * @Package: com.example.common
 * @ClassName: ActiveUser
 * @Author: 游佳琪
 * @Description: 用户权限
 * @Date: 2020-8-13 09:56
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser {
    private User user;
//    角色
    private List<String> roles;
//    权限
    private List<String> permissions;
}
