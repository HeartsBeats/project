package com.bus.domain;

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
 * @Package: com.bus.domain
 * @ClassName: Customer
 * @Author: 游佳琪
 * @Description: 进货实体类
 * @Date: 2020-9-3 10:40
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_outport")
public class Outport implements Serializable {
    private static final long serialVersionUID = 1L;
    //  id自增长
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String paytype;

    private Date outputtime;

    private String operateperson;

    private Integer number;

    private String remark;

    private String outportprice;

    private Integer providerid;

    private Integer goodsid;

    @TableField(exist = false)
    private String providername;

    @TableField(exist = false)
    private String goodsname;

    @TableField(exist = false)
    private String size;//规格
}
