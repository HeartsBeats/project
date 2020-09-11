package com.bus.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.print.DocFlavor;
import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.domain
 * @ClassName: Customer
 * @Author: 游佳琪
 * @Description: 顾客实体类
 * @Date: 2020-9-3 10:40
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    //  id自增长
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String customername;

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
