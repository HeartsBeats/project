package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ProjectName: project-demo
 * @Package: com.example.entity
 * @ClassName: permission
 * @Author: 游佳琪
 * @Description: permission实体类
 * @Date: 2020-8-14 10:53
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
public class Permission implements Serializable {
//  序列化ID 用于表示数据的唯一性
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pid;

    /**
     * 权限类型[menu/permission]
     */
    private String type;

    private String title;

    /**
     * 权限编码[只有type= permission才有  user:view]
     */
    private String percode;

    private String icon;

    private String href;

    private String target;

    private Integer open;

    private Integer ordernum;

    /**
     * 状态【0不可用1可用】
     */
    private Integer available;


}
