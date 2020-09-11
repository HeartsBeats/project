package com.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bus.domain.Provider;
import com.bus.service.ProviderService;
import com.bus.vo.ProviderVo;
import com.example.common.Constast;
import com.example.common.DataGridView;
import com.example.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.controller
 * @ClassName: ProviderController
 * @Author: 游佳琪
 * @Description: Provider 前端控制层
 * @Date: 2020-9-3 16:13
 * @Version: 1.0
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    /**
     * 功能描述: <br>
     * 〈〉           全查询
     *
     * @Param:
     * @Return:
     * @Author: YJQ
     * @Date: 2020-8-17 09:11
     */
    @RequestMapping("/loadAllProvider")
    public DataGridView loadAllProvider(ProviderVo providerVo) {
//        创建选择器
        QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
//        创建page,传入当前页和限制页数据
        IPage<Provider> page = new Page<>(providerVo.getPage(), providerVo.getLimit());
//        设置查询条件
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getProvidername()), "providername", providerVo.getProvidername());
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getConnectionperson()), "connectionperson", providerVo.getConnectionperson());
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getPhone()), "phone", providerVo.getPhone());
        queryWrapper.orderByAsc("id");
        this.providerService.page(page, queryWrapper);
//        以分页的形式返回查询结果数据
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 功能描述: <br>
     * 〈〉           添加客户
     * @Param: [ProviderVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-9-4 21:10
     */

    @RequestMapping("/addProvider")
    public ResultObj addProvider(ProviderVo providerVo) {
        try {
            this.providerService.save(providerVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_ERROR;
        }
    }
    /**
     * 功能描述: <br>
     * 〈〉           更新客户信息
     * @Param: [ProviderVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-9-4 21:10
     */

    @RequestMapping("updateProvider")
    public ResultObj updateProvider(ProviderVo providerVo) {
        try {
            this.providerService.updateById(providerVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉       删除操作
     *
     * @Param: [ProviderVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-18 15:28
     */
    @RequestMapping("/deleteProvider")
    public ResultObj deleteProvider(ProviderVo providerVo) {
        try {
            this.providerService.removeById(providerVo.getId());
            return ResultObj.DELETE_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 功能描述: <br>
     * 〈〉           批量删除
     *
     * @Param: [ProviderVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-18 15:36
     */

    @RequestMapping("/batchDeleteProvider")
    public ResultObj batchDeleteProvider(ProviderVo providerVo) {
        try {
            Collection<Serializable> idlist = new ArrayList<>();
            for (Integer id : providerVo.getIds()) {
                idlist.add(id);
            }
            this.providerService.removeByIds(idlist);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }
    /**
     * 加载所有可用的供应商
     */
    @RequestMapping("loadAllProviderForSelect")
    public DataGridView loadAllProviderForSelect() {
        QueryWrapper<Provider> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Provider> list = this.providerService.list(queryWrapper);
        return new DataGridView(list);
    }
}
