package com.example.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Constast;
import com.example.common.DataGridView;
import com.example.common.PinyinUtils;
import com.example.common.ResultObj;
import com.example.entity.Dept;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.service.DeptService;
import com.example.service.RoleService;
import com.example.service.UserService;
import com.example.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: project-demo
 * @Package: com.example.controller
 * @ClassName: UserController
 * @Author: 游佳琪
 * @Description: user对象控制器
 * @Date: 2020-8-13 08:52
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/loadAllUser")
    public DataGridView loadAllUser(UserVo userVo) {
        IPage<User> iPage = new Page<>(userVo.getPage(), userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getName()), "name", userVo.getName())
                .or().eq(StringUtils.isNotBlank(userVo.getLoginname()), "loginname", userVo.getLoginname());
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getAddress()), "address", userVo.getAddress());
        queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);
        queryWrapper.eq(userVo.getDeptid() != null, "deptid", userVo.getDeptid());
        this.userService.page(iPage, queryWrapper);
//        将查询到的部门id和直属领导id进行转换
        List<User> users = iPage.getRecords();
        for (User user : users) {
            if (user.getDeptid() != null) {
//                根据部门id查找到相关部门
                Dept dept = deptService.getById(user.getDeptid());
//                将查询到的部门名称与查到的用户信息中的部门id进行转换
                user.setDeptname(dept.getTitle());
            }
            if (user.getMgr() != null) {
//                根据部门id查找到相关部门
                User user1 = userService.getById(user.getMgr());
//                将查询到的部门名称与查到的用户信息中的部门id进行转换
                user.setLeadername(user1.getName());
            }
        }
        return new DataGridView(iPage.getTotal(), users);
    }

    /**
     * 功能描述: <br>
     * 〈〉       获取最大的排序码
     *
     * @Param: []
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: YJQ
     * @Date: 2020-8-21 21:41
     */

    @RequestMapping("/loadUserMaxOrderNum")
    public Map<String, Object> loadDeptMaxOrderNum() {
        Map<String, Object> map = new HashMap<String, Object>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        List<User> list = this.userService.list(queryWrapper);
        if (list.size() > 0) {
            map.put("value", list.get(0).getOrdernum() + 1);
        } else {
            map.put("value", 1);
        }
        return map;
    }

    /**
     * 功能描述: <br>
     * 〈〉           添加用户
     *
     * @Param: [roleVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-20 08:24
     */

    @RequestMapping("/addUser")
    public ResultObj addRole(UserVo userVo) {
        try {
            userVo.setType(Constast.USER_TYPE_NORMAL);//设置类型
            userVo.setHiredate(new Date());
            String salt = IdUtil.simpleUUID().toUpperCase();
            userVo.setSalt(salt);//设置盐
            userVo.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD, salt, 2).toString());//设置密码
            this.userService.save(userVo);
            return ResultObj.ADD_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }

    }

    /**
     * 把用户名转成拼音
     */
    @RequestMapping("/changeChineseToPinyin")
    public Map<String, Object> changeChineseToPinyin(String username) {
        Map<String, Object> map = new HashMap<>();
        if (null != username) {
            map.put("value", PinyinUtils.getPingYin(username));
        } else {
            map.put("value", "");
        }
        return map;
    }

    /**
     * 根据用户ID查询一个用户
     */
    @GetMapping("/loadUserById")
    public DataGridView loadUserById(Integer id) {
        log.info("Now Send Query By User ID!");
        return new DataGridView(this.userService.getById(id));
    }

    /**
     * 根据通过部门ID查询用户
     */
    @RequestMapping("/loadUsersByDeptId")
    public DataGridView loadUsersByDeptId(Integer deptid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(deptid != null, "deptid", deptid);
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);
        List<User> list = this.userService.list(queryWrapper);
        System.out.println(132131);
        return new DataGridView(list);
    }

    /**
     * 更新用户
     */
    @RequestMapping("/updateUser")
    public ResultObj updateUser(UserVo userVo) {
        try {
            this.userService.updateById(userVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 更新用户
     */
    @RequestMapping("/saveUserRole")
    public ResultObj saveUserRole(Integer uid, Integer[] ids) {
        try {
            this.userService.saveUserRole(uid, ids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }
    /*
     * 功能描述: <br>
     * 〈〉
     * @Param: 判断该部门是否有子部门
     * @Return:
     * @Author: YJQ
     * @Date: 2020-8-22 10:17
     */

    @RequestMapping("checkUserHasChildrenNode")
    public Map<String, Object> checkUserHasChildrenNode(UserVo userVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("mgr", userVo.getId());
        int count = this.userService.count(queryWrapper);
        if (count > 0) {
            map.put("value", true);
        } else {
            map.put("value", false);
        }
        return map;
    }

    /**
     * 删除用户
     */
    @RequestMapping("/deleteUser")
    public ResultObj deleteUser(Integer id) {
        try {
            this.userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 重置用户密码
     */
    @RequestMapping("resetPwd")
    public ResultObj resetPwd(Integer id) {
        try {
            User user = new User();
            user.setId(id);
            String salt = IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);//设置盐
            user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD, salt, 2).toString());//设置密码
            this.userService.updateById(user);
            return ResultObj.RESET_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.RESET_ERROR;
        }
    }

    /**
     * 通过用户id初始化角色列表
     */
    @RequestMapping("initRoleByUserId")
    public DataGridView initRoleByUserId(Integer id) {
        //1,查询所有可用的角色
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Map<String, Object>> listMaps = this.roleService.listMaps(queryWrapper);
        //2,查询当前用户拥有的角色ID集合
        List<Integer> rids = this.roleService.queryUserRoleIdsByUid(id);
//        遍历可用角色集合，初始化该用户已拥有的角色集合
        for (Map<String, Object> map : listMaps) {
            Boolean LAY_CHECKED = false;
            Integer roleId = (Integer) map.get("id");
            for (Integer rid : rids) {
                if (rid == roleId) {
                    LAY_CHECKED = true;
                    break;
                }
            }
            map.put("LAY_CHECKED", LAY_CHECKED);
        }
        return new DataGridView(Long.valueOf(listMaps.size()), listMaps);

    }


}

