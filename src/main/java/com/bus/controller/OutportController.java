package com.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bus.domain.Goods;
import com.bus.domain.Outport;
import com.bus.domain.Provider;
import com.bus.service.GoodsService;
import com.bus.service.OutportService;
import com.bus.service.ProviderService;
import com.bus.vo.OutportVo;
import com.example.common.Constast;
import com.example.common.DataGridView;
import com.example.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.controller
 * @ClassName: OutportController
 * @Author: 游佳琪
 * @Description: Outport 前端控制层
 * @Date: 2020-9-3 16:13
 * @Version: 1.0
 */
@RestController
@RequestMapping("/outport")
public class OutportController {
    @Autowired
    private OutportService outportService;
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
    @RequestMapping("/loadAllOutport")
    public DataGridView loadAllOutport(OutportVo outportVo) {
//        创建选择器
        QueryWrapper<Outport> queryWrapper = new QueryWrapper<>();
//        创建page,传入当前页和限制页数据
        IPage<Outport> page = new Page<>(outportVo.getPage(), outportVo.getLimit());
//        设置查询条件
        queryWrapper.eq((outportVo.getProviderid() != null && outportVo.getProviderid() > 0), "providerid", outportVo.getProviderid());
        queryWrapper.eq((outportVo.getGoodsid() != null && outportVo.getGoodsid() > 0), "goodsid", outportVo.getGoodsid());
        queryWrapper.ge(outportVo.getStartTime() != null, "outputtime", outportVo.getStartTime());
        queryWrapper.le(outportVo.getEndTime() != null, "outputtime", outportVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(outportVo.getOperateperson()), "operateperson", outportVo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(outportVo.getRemark()), "remark", outportVo.getRemark());
        this.outportService.page(page, queryWrapper);
        List<Outport> records = page.getRecords();
        for (Outport outport : records) {
            Provider provider = this.providerService.getById(outport.getProviderid());
            if (null != provider) {
                outport.setProvidername(provider.getProvidername());
            }
            Goods goods = this.goodsService.getById(outport.getGoodsid());
            if (null != goods) {
                outport.setGoodsname(goods.getGoodsname());
            }
        }
//        以分页的形式返回查询结果数据
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 功能描述: <br>
     * 〈〉           添加销售
     *
     * @Param: [outportVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-9-4 21:10
     */

    @RequestMapping("/addOutport")
    public ResultObj addOutport(Integer id,Integer currentnumber,String remark) {
        try {
            this.outportService.addOutPort(id,currentnumber,remark);
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
     * @Param: [outportVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-9-4 21:10
     */

    @RequestMapping("updateOutport")
    public ResultObj updateOutport(OutportVo outportVo) {
        try {
            this.outportService.updateById(outportVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉       删除操作
     *
     * @Param: [outportVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-18 15:28
     */
    @RequestMapping("/deleteOutport")
    public ResultObj deleteOutport(OutportVo outportVo) {
        try {
            this.outportService.removeById(outportVo.getId());
            return ResultObj.DELETE_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }
    
    /**
     *  用过供应商id查询商品信息
     * @param providerId
     * @return
     */
    @RequestMapping("/loadOutportByProviderId")
    public DataGridView loadOutportByProviderId(Integer providerId){
        QueryWrapper<Outport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(providerId!=null,"providerid",providerId);
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Outport> list = outportService.list(queryWrapper);
        for (Outport outport : list) {
            Provider provider = providerService.getBaseMapper().selectById(outport.getProviderid());
            if (null != provider) {
                outport.setProvidername(provider.getProvidername());
            }
        }
        return new DataGridView(list);
    }
}
