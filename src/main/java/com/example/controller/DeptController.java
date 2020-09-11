package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.DataGridView;
import com.example.common.ResultObj;
import com.example.common.TreeNode;
import com.example.entity.Dept;
import com.example.service.DeptService;
import com.example.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @ProjectName: project-demo
 * @Package: com.example.controller
 * @ClassName: DeptController
 * @Author: 游佳琪
 * @Description: 部门管理前端控制器
 * @Date: 2020-8-21 09:09
 * @Version: 1.0
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 功能描述: <br>
     * 〈〉           加载部门管理左边树的json
     *
     * @Param: [deptVo]
     * @Return: com.example.common.DataGridView
     * @Author: YJQ
     * @Date: 2020-8-21 10:10
     */

    @RequestMapping("/loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(DeptVo deptVo) {
        List<Dept> depts = this.deptService.list();
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Dept dept : depts) {
            boolean spread = dept.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(dept.getId(), dept.getPid(), dept.getTitle(), spread));
        }
//          返回查询数据json集
        return new DataGridView(treeNodes);
    }

    /**
     * 功能描述: <br>
     * 〈〉       查询部门信息
     *
     * @Param: [deptVo]
     * @Return: com.example.common.DataGridView
     * @Author: YJQ
     * @Date: 2020-8-21 17:08
     */

    @RequestMapping("/loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo) {
//        创建选择器
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
//        创建page,传入当前页和限制页数据
        IPage<Dept> page = new Page<>(deptVo.getPage(), deptVo.getLimit());
//        设置查询条件
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()), "title", deptVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getAddress()), "address", deptVo.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getRemark()), "remark", deptVo.getRemark());
//        查询部门管理左导航树点击部门的id，查询出该部门及其下属部门的信息
        queryWrapper.eq(deptVo.getId() != null, "id", deptVo.getId()).or().eq(deptVo.getId() != null, "pid", deptVo.getId());
//       查询结果排序
        queryWrapper.orderByAsc("ordernum");
        this.deptService.page(page, queryWrapper);
//        以分页的形式返回查询结果数据
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 功能描述: <br>
     * 〈〉       更新信息操作
     *
     * @Param: [deptVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-21 21:38
     */

    @RequestMapping("/updateDept")
    public ResultObj updateDept(DeptVo deptVo) {
        try {
            this.deptService.updateById(deptVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉       添加部门信息操作
     *
     * @Param: [deptVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-21 21:40
     */

    @RequestMapping("/addDept")
    public ResultObj addDept(DeptVo deptVo) {
        try {
            deptVo.setCreatetime(new Date());
            this.deptService.save(deptVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉       获取最大的排序码
     *
     * @Param: []
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: YJQ
     * @Date: 2020-8-21 21:41
     */

    @RequestMapping("/loadDeptMaxOrderNum")
    public Map<String, Object> loadDeptMaxOrderNum() {
        Map<String, Object> map = new HashMap<String, Object>();

        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        List<Dept> list = this.deptService.list(queryWrapper);
        if (list.size() > 0) {
            map.put("value", list.get(0).getOrdernum() + 1);
        } else {
            map.put("value", 1);
        }
        return map;
    }
    /*
     * 功能描述: <br>
     * 〈〉
     * @Param: 判断该部门是否有子部门
     * @Return:
     * @Author: YJQ
     * @Date: 2020-8-22 10:17
     */

    @RequestMapping("/checkDeptHasChildrenNode")
    public Map<String, Object> checkDeptHasChildrenNode(DeptVo deptVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        QueryWrapper<Dept> queryWrapper = new QueryWrapper();
        queryWrapper.eq("pid", deptVo.getId());
        int count = this.deptService.count(queryWrapper);
        if (count > 0) {
            map.put("value", true);
        } else {
            map.put("value", false);
        }
        return map;
    }

    @RequestMapping("/deleteDept")
    public ResultObj deleteDept(DeptVo deptVo){
        try {
            this.deptService.removeById(deptVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

}
