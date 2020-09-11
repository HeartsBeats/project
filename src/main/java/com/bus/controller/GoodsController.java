package com.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bus.domain.Goods;
import com.bus.domain.Provider;
import com.bus.service.GoodsService;
import com.bus.service.ProviderService;
import com.bus.vo.GoodsVo;
import com.example.common.AppFileUtil;
import com.example.common.Constast;
import com.example.common.DataGridView;
import com.example.common.ResultObj;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.controller
 * @ClassName: GoodsController
 * @Author: 游佳琪
 * @Description: Goods 前端控制层
 * @Date: 2020-9-3 16:13
 * @Version: 1.0
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
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
    @RequestMapping("/loadAllGoods")
    public DataGridView loadAllGoods(GoodsVo goodsVo) {
//        创建选择器
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        创建page,传入当前页和限制页数据
        IPage<Goods> page = new Page<>(goodsVo.getPage(), goodsVo.getLimit());
//        设置查询条件
        queryWrapper.eq(goodsVo.getProviderid() != null && goodsVo.getProviderid() != 0, "providerid", goodsVo.getProviderid());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getGoodsname()), "goodsname", goodsVo.getGoodsname());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getProductcode()), "productcode", goodsVo.getProductcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getPromitcode()), "promitcode", goodsVo.getPromitcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getDescription()), "description", goodsVo.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getSize()), "size", goodsVo.getSize());
        this.goodsService.page(page, queryWrapper);
        List<Goods> records = page.getRecords();
        for (Goods goods : records) {
            Provider provider = this.providerService.getById(goods.getProviderid());
            if (null != provider) {
                goods.setProvidername(provider.getProvidername());
            }
        }
//        以分页的形式返回查询结果数据
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 功能描述: <br>
     * 〈〉           添加客户
     *
     * @Param: [goodsVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-9-4 21:10
     */

    @RequestMapping("/addGoods")
    public ResultObj addGoods(GoodsVo goodsVo) {
        try {
            if(goodsVo.getGoodsname()!=null&&goodsVo.getGoodsimg().equals(Constast.IMAGES_DEFAULTGOODSIMG_PNG)){
                String renameFile = AppFileUtil.renameFile(goodsVo.getGoodsimg());
                goodsVo.setGoodsimg(renameFile);
            }
            this.goodsService.save(goodsVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉           更新客户信息
     *
     * @Param: [goodsVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-9-4 21:10
     */

    @RequestMapping("updateGoods")
    public ResultObj updateGoods(GoodsVo goodsVo) {
        try {
            if(goodsVo.getGoodsimg()!=null&&goodsVo.getGoodsimg().equals(Constast.IMAGES_DEFAULTGOODSIMG_PNG)){
                if(goodsVo.getGoodsimg().endsWith("_atemp")){
                    String renameFile = AppFileUtil.renameFile(goodsVo.getGoodsimg());
                    goodsVo.setGoodsimg(renameFile);
//                    删除原本的图片
                    String OldPath = this.goodsService.getById(goodsVo.getId()).getGoodsimg();
                    if(!OldPath.equals(Constast.IMAGES_DEFAULTGOODSIMG_PNG)){
                        AppFileUtil.removeFileByPath(OldPath);
                    }
                }
            }
            this.goodsService.updateById(goodsVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉       删除操作
     *
     * @Param: [goodsVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-18 15:28
     */
    @RequestMapping("/deleteGoods")
    public ResultObj deleteGoods(GoodsVo goodsVo) {
        try {
            this.goodsService.removeById(goodsVo.getId());
            return ResultObj.DELETE_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 功能描述: <br>
     * 〈〉   加载所有可用的供应商
     *
     * @Param:
     * @Return:
     * @Author: YJQ
     * @Date: 2020-9-7 14:29
     */
    @RequestMapping("/loadAllGoodsForSelect")
    public DataGridView loadAllGoodsForSelect() {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Goods> list = goodsService.list(queryWrapper);
        for (Goods goods : list) {
            Provider provider = providerService.getBaseMapper().selectById(goods.getProviderid());
            if (null != provider) {
                goods.setProvidername(provider.getProvidername());
            }
        }
        return new DataGridView(list);
    }

    /**
     *  用过供应商id查询商品信息
     * @param providerId
     * @return
     */
    @RequestMapping("/loadGoodsByProviderId")
    public DataGridView loadGoodsByProviderId(Integer providerId){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(providerId!=null,"providerid",providerId);
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Goods> list = goodsService.list(queryWrapper);
        for (Goods goods : list) {
            Provider provider = providerService.getBaseMapper().selectById(goods.getProviderid());
            if (null != provider) {
                goods.setProvidername(provider.getProvidername());
            }
        }
        return new DataGridView(list);
    }
}
