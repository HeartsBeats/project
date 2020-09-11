package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @ClassName: Dept
 * @Author: 游佳琪
 * @Description: 部门信息实体类
 * @Date: 2020-8-21 08:55
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dept")
@Accessors(chain = true)
public class Dept implements Serializable {

    private final static long serialVersionUid = 1l;
@TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String title;
    private Integer open;
    private String remark;
    private String address;
    /**
     * 状态【0不可用1可用】
     */
    private Integer available;
    /**
     * 排序码【为了调事显示顺序】
     */
    private Integer ordernum;
    private Date createtime;
}
