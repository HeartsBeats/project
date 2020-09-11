package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.DataGridView;
import com.example.common.ResultObj;
import com.example.entity.Loginfo;
import com.example.service.LoginfoService;
import com.example.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @ProjectName: project-demo
 * @Package: com.example.controller
 * @ClassName: LoginfoController
 * @Author: 游佳琪
 * @Description: loginfo控制器
 * @Date: 2020-8-17 09:07
 * @Version: 1.0
 */
@RestController
@RequestMapping("loginfo")
public class LoginfoController {
    @Autowired
    private LoginfoService loginfoService;

    /**
     * 功能描述: <br>
     * 〈〉           全查询
     *
     * @Param:
     * @Return:
     * @Author: YJQ
     * @Date: 2020-8-17 09:11
     */
    @RequestMapping("loadAllLoginfo")
    public DataGridView loadAllLoginfo(LoginfoVo loginfoVo) {
//        创建选择器
        QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<>();
//        创建page,传入当前页和限制页数据
        IPage<Loginfo> page = new Page<>(loginfoVo.getPage(), loginfoVo.getLimit());
//        设置查询条件
        queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginname()), "loginname", loginfoVo.getLoginname());
        queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginip()), "loginip", loginfoVo.getLoginip());
//        日志时间不为空并且大于等于开始时间
        queryWrapper.ge(loginfoVo.getStartTime() != null, "logintime", loginfoVo.getStartTime());
//        日志时间不为空并且小于等于结束时间
        queryWrapper.le(loginfoVo.getEndTime() != null, "logintime", loginfoVo.getEndTime());
//       查询结果时间排序
        queryWrapper.orderByDesc("logintime");
        this.loginfoService.page(page, queryWrapper);
//        以分页的形式返回查询结果数据
        return new DataGridView(page.getTotal(), page.getRecords());
    }
    /**
     * 功能描述: <br>
     * 〈〉       删除操作
     * @Param: [loginfoVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-18 15:28
     */
    @RequestMapping("deleteLoginfo")
    public ResultObj deleteLoginfo(LoginfoVo loginfoVo){
        try {
            this.loginfoService.removeById(loginfoVo.getId());
            return ResultObj.DELETE_SUCCESS;

        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }
    /**
     * 功能描述: <br>
     * 〈〉           批量删除
     * @Param: [loginfoVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-18 15:36
     */

    @RequestMapping("batchDeleteLoginfo")
    public ResultObj batchDeleteLoginfo(LoginfoVo loginfoVo){
        try {
            Collection<Serializable> idlist = new ArrayList<>();
            for (Integer id:loginfoVo.getIds()){
                idlist.add(id);
            }
            this.loginfoService.removeByIds(idlist);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }
}
