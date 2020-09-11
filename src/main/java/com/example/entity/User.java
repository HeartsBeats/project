package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: project-demo
 * @Package: com.example.entity
 * @ClassName: user
 * @Author: 游佳琪
 * @Description: user实体类
 * @Date: 2020-8-12 20:39
 * @Version: 1.0
 */
@Data
//@EqualsAndHashCode：作用于类，覆盖默认的equals和hashCode
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    //  id自增长
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String loginname;

    private String address;

    private Integer sex;

    private String remark;

    private String pwd;

    private Integer deptid;

    private Date hiredate;

    private Integer mgr;

    private Integer available;

    private Integer ordernum;

    /**
     * 用户类型[0超级管理员1，管理员，2普通用户]
     */
    private Integer type;

    /**
     * 头像地址
     */
    private String imgpath;

    private String salt;

    //    部门名称根据部门id转换，实际sys_user表中不存在该属性
    @TableField(exist = false)
    private String deptname;

    //   直属领导名称根据mgr属性转换，实际sys_user表中不存在该属性
    @TableField(exist = false)
    private String leadername;
}
