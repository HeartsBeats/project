package com.bus.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.domain
 * @ClassName: Customer
 * @Author: 游佳琪
 * @Description: 商品实体类
 * @Date: 2020-9-3 10:40
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_goods")
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;
    //  id自增长
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String goodsname;

    private String produceplace;

    private String size;

    private String goodspackage;

    private String productcode;

    private String promitcode;

    private String description;

    private String goodsimg;


    private Double price;

    private Integer number;

    private Integer dangernum;

    private Integer available;

    private Integer providerid;

    @TableField(exist = false)
    private String providername;
}
