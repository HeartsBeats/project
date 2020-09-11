package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @ProjectName: project-demo
 * @Package: com.example.controller
 * @ClassName: systemController
 * @Author: 游佳琪
 * @Description: 作为跳转页面的控制台
 * @Date: 2020-8-13 20:37
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys")
public class systemController {
    /**
     * 功能描述: <br>
     * 〈〉   首页跳转到登录页面
     *
     * @Param:
     * @Return:
     * @Author: YJQ
     * @Date: 2020-8-13 20:38
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "system/index/login";
    }

    /**
     * 功能描述: <br>
     * 〈〉       跳转到首页
     *
     * @Param:
     * @Return:
     * @Author: YJQ
     * @Date: 2020-8-13 21:16
     */
    @RequestMapping("index")
    public String login() {
        return "system/index/index";
    }

    /**
     * 功能描述: <br>
     * 〈〉   跳转到操作台
     *
     * @Param: []
     * @Return: java.lang.String
     * @Author: YJQ
     * @Date: 2020-8-18 10:26
     */

    @RequestMapping("toDeskManager")
    public String toDeskManager() {
        return "system/index/deskManager";
    }

    /**
     * 功能描述: <br>
     * 〈〉          跳转到日志管理页面
     *
     * @Param: []
     * @Return: java.lang.String
     * @Author: YJQ
     * @Date: 2020-8-18 10:26
     */

    @RequestMapping("toLoginfoManager")
    public String toLoginfoManager() {
        return "system/loginfo/loginfoManager";
    }

    /**
     * 功能描述: <br>
     * 〈〉       跳转到公告管理页面
     *
     * @Param: []
     * @Return: java.lang.String
     * @Author: YJQ
     * @Date: 2020-8-21 10:07
     */

    @RequestMapping("toNoticeManager")
    public String toNoticeManager() {
        return "system/notice/toNoticeManager";
    }

    /**
     * 功能描述: <br>
     * 〈〉       跳转到部门管理页面
     *
     * @Param: []
     * @Return: java.lang.String
     * @Author: YJQ
     * @Date: 2020-8-21 10:32
     */

    @RequestMapping("toDeptManager")
    public String toDeptManager() {
        return "system/dept/DeptManager";
    }

    /**
     * 跳转到部门管理-left
     */
    @RequestMapping("toDeptLeft")
    public String toDeptLeft() {
        return "system/dept/DeptLeft";
    }


    /**
     * 跳转到部门管理--right
     */
    @RequestMapping("toDeptRight")
    public String toDeptRight() {
        return "system/dept/DeptRight";
    }

    /**
     * 跳转到菜单管理
     */
    @RequestMapping("toMenuManager")
    public String toMenuManager() {
        return "system/menu/menuManager";
    }

    /**
     * 跳转到菜单管理-left
     */
    @RequestMapping("toMenuLeft")
    public String toMenuLeft() {
        return "system/menu/menuLeft";
    }

    /**
     * 跳转到菜单管理--right
     */
    @RequestMapping("toMenuRight")
    public String toMenuRight() {
        return "system/menu/menuRight";
    }

    /**
     * 跳转到权限管理
     */
    @RequestMapping("toPermissionManager")
    public String toPermissionManager() {
        return "system/permission/permissionManager";
    }

    /**
     * 跳转到权限管理-left
     */
    @RequestMapping("toPermissionLeft")
    public String toPermissionLeft() {
        return "system/permission/permissionLeft";
    }

    /**
     * 跳转到权限管理--right
     */
    @RequestMapping("toPermissionRight")
    public String toPermissionRight() {
        return "system/permission/permissionRight";
    }

    /**
     * 跳转到角色管理
     */
    @RequestMapping("toRoleManager")
    public String toRoleManager() {
        return "system/role/toRoleManager";
    }
    /**
     * 跳转到用户管理
     */
    @RequestMapping("toUserManager")
    public String toUserManager() {
        return "system/user/toUserManager";
    }
    /**
     * 跳转到用户管理
     */
    @RequestMapping("toCacheManager")
    public String toCacheManager() {
        return "system/cache/toCacheManager";
    }
    /**
     * 跳转到用户个人信息
     */
    @RequestMapping("toUserInfo")
    public String toUserInfo() {
        return "system/user/userInfo";
    }
}
