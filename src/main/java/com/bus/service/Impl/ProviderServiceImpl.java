package com.bus.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bus.domain.Provider;
import com.bus.mapper.ProviderMapper;
import com.bus.service.ProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.service.Impl
 * @ClassName: ProviderServiceImpl
 * @Author: 游佳琪
 * @Description: Provider 服务层接口实现类
 * @Date: 2020-9-3 16:07
 * @Version: 1.0
 */
@Service
@Transactional
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements ProviderService {
    @Override
    public boolean save(Provider entity) {
        return super.save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public Provider getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean updateById(Provider entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }
}
