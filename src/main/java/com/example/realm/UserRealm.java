package com.example.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.ActiveUser;
import com.example.common.Constast;
import com.example.entity.Permission;
import com.example.entity.User;
import com.example.service.PermissionService;
import com.example.service.RoleService;
import com.example.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ProjectName: project-demo
 * @Package: com.example.realm
 * @ClassName: UserRealm
 * @Author: 游佳琪
 * @Description: 用户权限鉴定
 * @Date: 2020-8-13 10:04
 * @Version: 1.0
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    @Lazy       //只有在使用时才会加载
    private UserService userService;

    @Autowired
    @Lazy       //只有在使用时才会加载
    private PermissionService permissionService;

    @Autowired
    @Lazy       //只有在使用时才会加载
    private RoleService roleService;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 功能描述: <br> 执行授权逻辑
     * 〈〉
     *
     * @Param: [principalCollection]
     * @Return: org.apache.shiro.authz.AuthorizationInfo
     * @Author: YJQ
     * @Date: 2020-8-13 10:13
     */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiveUser activeUser = (ActiveUser) principalCollection.getPrimaryPrincipal();
        User user = activeUser.getUser();
        List<String> permissions = activeUser.getPermissions();
        if (user.getType() == Constast.USER_TYPE_SUPER) {
            authorizationInfo.addStringPermission("*:*");
        } else {
            if (null != permissions && permissions.size() > 0) {
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
    }

    /**
     * 功能描述: <br> 执行认证逻辑
     * 〈〉
     *
     * @Param: [authenticationToken]
     * @Return: org.apache.shiro.authc.AuthenticationInfo
     * @Author: YJQ
     * @Date: 2020-8-13 10:14
     */

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname", authenticationToken.getPrincipal().toString());
        User user = userService.getOne(queryWrapper);
        if (user != null) {
            ActiveUser activeUser = new ActiveUser();
            activeUser.setUser(user);
            //根据用户ID查询percode
            //查询所有菜单
            QueryWrapper<Permission> qw = new QueryWrapper<>();
            //设置只能查询菜单
            qw.eq("type", Constast.TYPE_PERMISSION);
            qw.eq("available", Constast.AVAILABLE_TRUE);
            //根据用户ID+角色+权限去查询
            //根据用户ID查询角色
            List<Integer> rids = roleService.queryUserRoleIdsByUid(user.getId());
//            由于得到的pid有重复出现的故用set集合存储，去重复,由于一个用户可能拥有不同的角色，角色所拥有的权限有重叠的部分故需要去重复
            Set<Integer> pids = new HashSet<>();
            if (rids.size() > 0) {
                for (Integer rid : rids) {
                    //根据角色ID取到权限和菜单ID
                    List<Integer> list = roleService.queryRolePermissionIdsByRid(rid);
                    pids.addAll(list);
                }
            }
            List<Permission> list = new ArrayList<>();
            //根据角色ID查询权限
            if (pids.size() > 0) {
                qw.in("id", pids);
                list = permissionService.list(qw);
            }
            List<String> percodes = new ArrayList<>();
            for (Permission permission : list) {
                percodes.add(permission.getPercode());
            }
            //放到
            activeUser.setPermissions(percodes);

            ByteSource cred = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, user.getPwd(), cred, this.getName());
            return info;
        }
        return null;
    }
}
