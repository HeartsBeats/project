package com.bus.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.domain
 * @ClassName: Provider
 * @Author: 游佳琪
 * @Description: 供货商实体类
 * @Date: 2020-9-4 22:01
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_provider")
public class Provider implements Serializable {
    private static final long serialVersionUID = 1L;
    //  id自增长
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String providername;

    private String zip;

    private String address;

    private String telephone;

    private String connectionperson;

    private String phone;

    private String bank;

    private String account;

    private String email;

    private String fax;

    private Integer available;
}
