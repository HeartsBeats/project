package com.example.vo;

import com.example.entity.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ProjectName: project-demo
 * @Package: com.example.vo
 * @ClassName: DeptVo
 * @Author: 游佳琪
 * @Description:
 * @Date: 2020-8-21 09:10
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVo extends Dept {

    private static final long serialVersionUID = 1L;


    private Integer page = 1;

    private Integer limit = 10;

}
