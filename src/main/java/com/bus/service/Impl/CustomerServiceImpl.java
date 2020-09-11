package com.bus.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bus.domain.Customer;
import com.bus.mapper.CustomerMapper;
import com.bus.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.service.Impl
 * @ClassName: CustomerServiceImpl
 * @Author: 游佳琪
 * @Description: Customer 服务层接口实现类
 * @Date: 2020-9-3 16:07
 * @Version: 1.0
 */
@Service
@Transactional
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Override
    public boolean save(Customer entity) {
        return super.save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public Customer getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean updateById(Customer entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }
}
