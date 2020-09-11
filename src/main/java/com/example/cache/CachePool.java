package com.example.cache;

import com.bus.domain.Customer;
import com.bus.domain.Goods;
import com.bus.domain.Provider;
import com.bus.mapper.CustomerMapper;
import com.bus.mapper.GoodsMapper;
import com.bus.mapper.ProviderMapper;
import com.example.common.SpringUtil;
import com.example.entity.Dept;
import com.example.entity.User;
import com.example.mapper.DeptMapper;
import com.example.mapper.UserMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: project-demo
 * @Package: com.example.cache
 * @ClassName: CachePool
 * @Author: 游佳琪
 * @Description: 缓存池
 * @Date: 2020-9-5 15:09
 * @Version: 1.0
 */
public class CachePool {
    /**
     * 所有的缓存数据放到这个CACHE_CONTAINER类似于redis
     * 可见性，是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的
     * 也就是一个线程修改的结果。另一个线程马上就能看到。比如：用volatile修饰的变量，就会具有可见性
     * 。volatile修饰的变量不允许线程内部缓存和重排序，即直接修改内存。所以对其他线程是可见的。
     * 但是这里需要注意一个问题，volatile只能让被他修饰内容具有可见性，但不能保证它具有原子性。
     * 比如 volatile int a = 0；之后有一个操作 a++；这个变量a具有可见性，但是a++ 依然是一个非原子操作，
     * 也就是这个操作同样存在线程安全问题。
     * <p>
     * 　　在 Java 中 volatile、synchronized 和 final 实现可见性。
     */
    public static volatile Map<String, Object> CACHE_CONTAINER = new HashMap<>();


    /**
     * 根据KEY删除缓存
     *
     * @param key
     */
    public static void removeCeachByKey(String key) {
        CACHE_CONTAINER.remove(key);
    }

    /**
     * 清空所有缓存
     *
     * @param
     */
    public static void removeAllCeach() {
        CACHE_CONTAINER.clear();

    }

    /**
     * 同步缓存
     */
    public static void syncData() {
//        同步部门数据
        DeptMapper deptMapper = SpringUtil.getBean(DeptMapper.class);
        List<Dept> depts = deptMapper.selectList(null);
        for (Dept dept : depts) {
            CACHE_CONTAINER.put("dept:" + dept.getId(), dept);
        }
//        同步用户数据
        UserMapper userMapper = SpringUtil.getBean(UserMapper.class);
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            CACHE_CONTAINER.put("user" + user.getId(), user);
        }
        //同步客户数据
        CustomerMapper customerMapper = SpringUtil.getBean(CustomerMapper.class);
        List<Customer> customerList = customerMapper.selectList(null);
        for (Customer customer : customerList) {
            CACHE_CONTAINER.put("customer:" + customer.getId(), customer);
        }
        //同步供应商数据
        ProviderMapper providerMapper = SpringUtil.getBean(ProviderMapper.class);
        List<Provider> providerList = providerMapper.selectList(null);
        for (Provider provider : providerList) {
            CACHE_CONTAINER.put("customer:" + provider.getId(), provider);
        }
        //同步商品数据
        GoodsMapper goodsMapper = SpringUtil.getBean(GoodsMapper.class);
        List<Goods> goodsList = goodsMapper.selectList(null);
        for (Goods goods : goodsList) {
            CACHE_CONTAINER.put("goods:" + goods.getId(), goods);
        }


    }

}
