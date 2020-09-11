package com.example.vo;

import com.example.entity.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ProjectName: project-demo
 * @Package: com.example.vo
 * @ClassName: PermissionVo
 * @Author: 游佳琪
 * @Description: Permission的子类
 * @Date: 2020-8-14 11:36
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission {
    //  序列化ID 用于表示数据的唯一性
    private static final long serialVersionUID = 1L;

    //  分页的配置  page:当前是第几页，limit:一页中获取的查询信息条数
    private Integer page = 1;
    private Integer limit = 10;

}
