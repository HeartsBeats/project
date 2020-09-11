package com.example.vo;

import com.example.entity.Role;
import com.example.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ProjectName: project-demo
 * @Package: com.example.vo
 * @ClassName: NoticeVo
 * @Author: 游佳琪
 * @Description:
 * @Date: 2020-8-18 20:43
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends User {

    private static final long serialVersionUID = 1l;

    //  分页的配置  page:当前是第几页，limit:一页中获取的查询信息条数
    private Integer page = 1;
    private Integer limit = 10;

}
