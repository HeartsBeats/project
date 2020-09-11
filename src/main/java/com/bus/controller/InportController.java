package com.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bus.domain.Goods;
import com.bus.domain.Inport;
import com.bus.domain.Provider;
import com.bus.service.GoodsService;
import com.bus.service.InportService;
import com.bus.service.ProviderService;
import com.bus.vo.InportVo;
import com.example.common.Constast;
import com.example.common.DataGridView;
import com.example.common.ResultObj;
import com.example.common.WebUtils;
import com.example.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.controller
 * @ClassName: InportController
 * @Author: 游佳琪
 * @Description: Inport 前端控制层
 * @Date: 2020-9-3 16:13
 * @Version: 1.0
 */
@RestController
@RequestMapping("/inport")
public class InportController {
    @Autowired
    private InportService inportService;
    @Autowired
    private ProviderService providerService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 功能描述: <br>
     * 〈〉           全查询
     *
     * @Param:
     * @Return:
     * @Author: YJQ
     * @Date: 2020-8-17 09:11
     */
    @RequestMapping("/loadAllInport")
    public DataGridView loadAllInport(InportVo inportVo) {
//        创建选择器
        QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();
//        创建page,传入当前页和限制页数据
        IPage<Inport> page = new Page<>(inportVo.getPage(), inportVo.getLimit());
//        设置查询条件
        queryWrapper.eq((inportVo.getProviderid() != null && inportVo.getProviderid() > 0), "providerid", inportVo.getProviderid());
        queryWrapper.eq((inportVo.getGoodsid() != null && inportVo.getGoodsid() > 0), "goodsid", inportVo.getGoodsid());
        queryWrapper.ge(inportVo.getStartTime() != null, "inporttime", inportVo.getStartTime());
        queryWrapper.le(inportVo.getEndTime() != null, "inporttime", inportVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(inportVo.getOperateperson()), "operateperson", inportVo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(inportVo.getRemark()), "remark", inportVo.getRemark());
        this.inportService.page(page, queryWrapper);
        List<Inport> records = page.getRecords();
        for (Inport inport : records) {
            Provider provider = this.providerService.getById(inport.getProviderid());
            if (null != provider) {
                inport.setProvidername(provider.getProvidername());
            }
            Goods goods = this.goodsService.getById(inport.getGoodsid());
            if (null != goods) {
                inport.setGoodsname(goods.getGoodsname());
            }
        }
//        以分页的形式返回查询结果数据
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加
     */
    @RequestMapping("/addInport")
    public ResultObj addInport(InportVo inportVo) {
        try {
            inportVo.setInporttime(new Date());
            User user = (User) WebUtils.getSession().getAttribute("user");
            this.inportService.save(inportVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉           更新销售信息
     *
     * @Param: [inportVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-9-4 21:10
     */

    @RequestMapping("updateInport")
    public ResultObj updateInport(InportVo inportVo) {
        try {
            this.inportService.updateById(inportVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉       删除操作
     *
     * @Param: [inportVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-18 15:28
     */
    @RequestMapping("/deleteInport")
    public ResultObj deleteInport(InportVo inportVo) {
        try {
            this.inportService.removeById(inportVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 用过供应商id查询商品信息
     *
     * @param providerId
     * @return
     */
    @RequestMapping("/loadInportByProviderId")
    public DataGridView loadInportByProviderId(Integer providerId) {
        QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(providerId != null, "providerid", providerId);
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Inport> list = inportService.list(queryWrapper);
        for (Inport inport : list) {
            Provider provider = providerService.getBaseMapper().selectById(inport.getProviderid());
            if (null != provider) {
                inport.setProvidername(provider.getProvidername());
            }
        }
        return new DataGridView(list);
    }
}
