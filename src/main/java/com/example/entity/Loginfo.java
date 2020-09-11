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
 * @ClassName: Loginfo
 * @Author: 游佳琪
 * @Description: loginfo实体类
 * @Date: 2020-8-16 22:26
 * @Version: 1.0
 */
@Data
//有多个类有相同的部分属性，把它们定义到父类中，恰好id（数据库主键）也在父类中，
// 那么就会存在部分对象在比较时，它们并不相等，却因为lombok自动生成的equals(Object other) 和 hashCode()方法判定为相等，从而导致出错
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_loginfo")
public class Loginfo implements Serializable {
    //    序列化标识
    private static final long serialVersionUID = 1L;
    //    指定主键 自增长
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String loginname;

    private String loginip;

    private Date logintime;


}
