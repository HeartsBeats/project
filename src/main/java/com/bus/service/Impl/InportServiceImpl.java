package com.bus.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bus.domain.Goods;
import com.bus.domain.Inport;
import com.bus.mapper.GoodsMapper;
import com.bus.mapper.InportMapper;
import com.bus.service.GoodsService;
import com.bus.service.InportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.service.Impl
 * @ClassName: InportServiceImpl
 * @Author: 游佳琪
 * @Description: Inport 服务层接口实现类
 * @Date: 2020-9-3 16:07
 * @Version: 1.0
 */
@Service
@Transactional
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements InportService {
    @Autowired
    private GoodsService goodsService;
    @Override
    public boolean save(Inport entity) {
        //根据商品编号查询商品
        Goods goods = this.goodsService.getBaseMapper().selectById(entity.getGoodsid());
        goods.setNumber(goods.getNumber() + entity.getNumber());
        this.goodsService.getBaseMapper().updateById(goods);
        //保存进货信息
        return super.save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据进货ID查询进货
        Inport inport = this.baseMapper.selectById(id);
        //根据商品ID查询商品信息
        Goods goods = this.goodsService.getBaseMapper().selectById(inport.getGoodsid());
        //库存的算法  当前库存-进货单数量
        goods.setNumber(goods.getNumber() - inport.getNumber());
        this.goodsService.getBaseMapper().updateById(goods);
        return super.removeById(id);
    }

    @Override
    public Inport getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean updateById(Inport entity) {
        //根据进货ID查询进货
        Inport inport = this.baseMapper.selectById(entity.getId());
        //根据商品ID查询商品信息
        Goods goods = this.goodsService.getBaseMapper().selectById(entity.getGoodsid());
        //库存的算法  当前库存-进货单修改之前的数量+修改之后的数量
        goods.setNumber(goods.getNumber() - inport.getNumber() + entity.getNumber());
        this.goodsService.getBaseMapper().updateById(goods);
        //更新进货单
        return super.updateById(entity);
    }

}
