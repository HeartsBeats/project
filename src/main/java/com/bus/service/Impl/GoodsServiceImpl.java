package com.bus.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bus.domain.Goods;
import com.bus.mapper.GoodsMapper;
import com.bus.service.GoodsService;
import com.bus.service.InportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
/**
 * @ProjectName: project-demo
 * @Package: com.bus.service.Impl
 * @ClassName: GoodsServiceImpl
 * @Author: 游佳琪
 * @Description: Goods 服务层接口实现类
 * @Date: 2020-9-3 16:07
 * @Version: 1.0
 */
@Service
@Transactional
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Override
    public boolean save(Goods entity) {
        return super.save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public Goods getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean updateById(Goods entity) {
        return super.updateById(entity);
    }

}
