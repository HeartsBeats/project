package com.bus.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bus.domain.Outport;
import com.bus.mapper.OutportMapper;
import com.bus.service.OutportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.service.Impl
 * @ClassName: OutportServiceImpl
 * @Author: 游佳琪
 * @Description: Outport 服务层接口实现类
 * @Date: 2020-9-3 16:07
 * @Version: 1.0
 */
@Service
@Transactional
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements OutportService {
    @Override
    public boolean save(Outport entity) {
        return super.save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public Outport getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean updateById(Outport entity) {
        return super.updateById(entity);
    }

    @Override
    public void addOutPort(Integer id, Integer currentnumber, String remark) {


    }
}
