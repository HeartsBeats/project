package com.example.controller;

import com.example.common.ActiveUser;
import com.example.common.ResultObj;
import com.example.common.WebUtils;
import com.example.entity.Loginfo;
import com.example.service.LoginfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ProjectName: project-demo
 * @Package: com.example.controller
 * @ClassName: loginController
 * @Author: 游佳琪
 * @Description: 登录控制器
 * @Date: 2020-8-13 21:18
 * @Version: 1.0
 */
@RestController
@RequestMapping("/login")
public class loginController {

    @Autowired
    private LoginfoService loginfoService;

    @RequestMapping("/login")
    public ResultObj login(String loginname, String pwd) {
/*
    使用shiro编写认证操作
    1.获取subject对象
    2.封装用户数据和验证码
    3.执行登录方法
 */
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(loginname, pwd);
        try {
            subject.login(token);
//            为空即未登录，获取登录成功的用户信息
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
//            将验证成功的用户信息存入session,为了降低耦合和减少对象的创建，引用web工具类
            WebUtils.getSession().setAttribute("user", activeUser.getUser());
            //记录登陆日志
            Loginfo entity = new Loginfo();
            entity.setLoginname(activeUser.getUser().getName()+"-"+activeUser.getUser().getLoginname());
            entity.setLogintime(new Date());
            //0:0:0:0:0:0:0:1是ipv6的表现形式，对应ipv4来说相当于127.0.0.1，也就是本机
            //如果项目部署在本机win7系统，访问时是通过 localhost 来访问，
            //用java获取ip地址可能会出现该问题，这时获取的ip将是 0:0:0:0:0:0:0:1
            entity.setLoginip(WebUtils.getRequest().getRemoteAddr());
            loginfoService.save(entity);
            return ResultObj.LOGIN_SUCCESS;
        } catch (AuthenticationException e) {
            e.printStackTrace();
//            为验证通过，返回错误信息
            return ResultObj.LOGIN_ERROR_PASS;
        }
    }
}
