package com.example.vo;

import com.example.entity.Loginfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ProjectName: project-demo
 * @Package: com.example.vo
 * @ClassName: LoginfoVo
 * @Author: 游佳琪
 * @Description: 视图对象，用于展示层，它的作用是把某个指定页面（或组件）的所有数据封装起来
 * @Date: 2020-8-17 09:12
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginfoVo extends Loginfo {

    private static final long serialVersionUID = 1L;

//  分页的配置  page:当前是第几页，limit:一页中获取的查询信息条数
    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;      //接收多个ID，用于批量操作


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
