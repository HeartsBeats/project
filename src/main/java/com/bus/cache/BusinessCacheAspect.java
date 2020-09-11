package com.bus.cache;


import com.bus.domain.Customer;
import com.bus.domain.Goods;
import com.bus.domain.Provider;
import com.example.cache.CachePool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.cache
 * @ClassName: CacheAspect
 * @Author: 游佳琪
 * @Description: 缓存 切面类  AOP代理
 * @Date: 2020-8-22 14:24
 * @Version: 1.0
 */
@Aspect
@Component
//基于注解的方式实现AOP需要在配置类中添加注解@EnableAspectJAutoProxy
@EnableAspectJAutoProxy
public class BusinessCacheAspect {


    /**
     * 日志出处
     */
    private Log log = LogFactory.getLog(BusinessCacheAspect.class);

    //声明一个缓存容器
    private static Map<String, Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;


//    public static Map<String, Object> getCACHE_CONTAINER(){
//        return CACHE_CONTAINER;
//    }

    /*
     * 功能描述: <br>
        Signature getSignature();	获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
        Object[] getArgs();	获取传入目标方法的参数对象
        Object getTarget();	获取被代理的对象
        Object getThis();	获取代理对象
     * 〈〉
     * @Param:
     * @Return:
     * @Author: YJQ
     * @Date: 2020-8-22 16:01
     */

    /**
     * 定义一个切入点表达式,用来确定哪些类需要代理
     * execution(* aopdemo.*.*(..))代表aopdemo包下所有类的所有方法都会被代理
     * 声明切入表达式
     */
    private static final String POINTCUT_CUSTOMER_UPDATE = "execution(* com.bus.service.Impl.CustomerServiceImpl.updateById(..))";
    private static final String POINTCUT_CUSTOMER_SAVE = "execution(* com.bus.service.Impl.CustomerServiceImpl.save(..))";
    private static final String POINTCUT_CUSTOMER_DELETE = "execution(* com.bus.service.Impl.CustomerServiceImpl.removeById(..))";
    private static final String POINTCUT_CUSTOMER_GET = "execution(* com.bus.service.Impl.CustomerServiceImpl.getById(..))";
    private static final String POINTCUT_CUSTOMER_BECHEDELETE = "execution(* com.bus.service.Impl.CustomerServiceImpl.removeByIds(..))";
    private static final String CACHE_CUSTOMER_PROFIX = "customer:";

    /**
     * 功能描述: <br>
     * 〈〉               查询单个对象切入
     *
     * @Param: [joinPoint]
     * @Return: java.lang.Object
     * @Author: YJQ
     * @Date: 2020-8-22 15:58
     */

    @Around(POINTCUT_CUSTOMER_GET)
    public Object cacheCustomerGet(ProceedingJoinPoint joinPoint) throws Throwable {
        /*
         * 环绕方法,可自定义目标方法执行的时机
         * @param pjd JoinPoint的子接口,添加了
         *            Object proceed() throws Throwable 执行目标方法
         *            Object proceed(Object[] var1) throws Throwable 传入的新的参数去执行目标方法
         *            两个方法
         * @return 此方法需要返回值,返回值视为目标方法的返回值
         */
//        获取第一个传入目标方法的参数对象，由于是查询则传入目标方法的参数是Integer类型
        Integer object = (Integer) joinPoint.getArgs()[0];
//        先从缓存容器中查看是否缓存了该数据，有则取出，无则去数据库中取出
        Object res1 = CACHE_CONTAINER.get(CACHE_CUSTOMER_PROFIX + object);
        if (res1 != null) {
//            缓存中存在该对象，故无需执行目标方法
            log.info("已从缓存里面找到客户对象" + CACHE_CUSTOMER_PROFIX + object);
            return res1;
        } else {
            //缓存中无此对象，故执行目标方法
            Customer res2 = (Customer) joinPoint.proceed();
//            执行结果存入缓存中
            CACHE_CONTAINER.put(CACHE_CUSTOMER_PROFIX + res2.getId(), res2);
            return res2;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉       更新方法切入
     *
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-22 16:36
     */

    @Around(POINTCUT_CUSTOMER_UPDATE)
    public boolean cacheCustomerUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
//        获取第一个传入参数
        Customer customerVo = (Customer) joinPoint.getArgs()[0];
//        先从执行目标方法更新数据库，之后更新缓存
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
//            若更新成功，则开始更新缓存
//            首先查看缓存中是否有更新对象的原数据
            Customer customer = (Customer) CACHE_CONTAINER.get(CACHE_CUSTOMER_PROFIX + customerVo.getId());
            if (customer == null) {
//                若无元数据，则直接存入更新对象
                customer = new Customer();
                BeanUtils.copyProperties(customerVo, customer);
                log.info("已更新客户对象" + CACHE_CUSTOMER_PROFIX + customer);
                CACHE_CONTAINER.put(CACHE_CUSTOMER_PROFIX + customer.getId(), customer);
            }
        }
        return isSuccess;
    }

    /**
     * 功能描述: <br>
     * 〈〉       删除客户对象切入
     *
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-27 10:02
     */

    @Around(POINTCUT_CUSTOMER_DELETE)
    public boolean cacheCustomerDelete(ProceedingJoinPoint joinPoint) throws Throwable {
//        获取第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
//        执行目标方法
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //删除成功，则删除缓存中对应的对象
            log.info("已从缓存中删除客户对象" + CACHE_CUSTOMER_PROFIX + id);
            CACHE_CONTAINER.remove(CACHE_CUSTOMER_PROFIX + id);
        }
        return isSuccess;
    }

    /**
     * 功能描述: <br>
     * 〈〉       批量删除客户对象切入
     *
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-27 10:02
     */

    @Around(POINTCUT_CUSTOMER_BECHEDELETE)
    public boolean cacheBecheDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        @SuppressWarnings("unchecked")
        Collection<Serializable> idList = (Collection<Serializable>) joinPoint.getArgs()[0];
//        执行目标方法
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            for (Serializable id : idList) {
                //删除成功，则删除缓存中对应的对象
                log.info("已从缓存中删除客户对象" + CACHE_CUSTOMER_PROFIX + id);
                CACHE_CONTAINER.remove(CACHE_CUSTOMER_PROFIX + id);
            }
        }
        return isSuccess;
    }

    /**
     * 功能描述: <br>
     * 〈〉       保存客户对象切入
     *
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-27 10:02
     */

    @Around(POINTCUT_CUSTOMER_SAVE)
    public boolean cacheCustomerSave(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        Customer object = (Customer) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            log.info("已更新缓存中客户对象" + CACHE_PROVIDER_PROFIX + object);
            CACHE_CONTAINER.put(CACHE_PROVIDER_PROFIX + object.getId(), object);
        }
        return res;
    }

    /*****************************供应商切入*****************************************
     /**
     * 定义一个切入点表达式,用来确定哪些类需要代理
     * execution(* aopdemo.*.*(..))代表aopdemo包下所有类的所有方法都会被代理
     * 声明切入表达式
     */
    private static final String POINTCUT_PROVIDER_UPDATE = "execution(* com.bus.service.Impl.ProviderServiceImpl.updateById(..))";
    private static final String POINTCUT_PROVIDER_GET = "execution(* com.bus.service.Impl.ProviderServiceImpl.getById(..))";
    private static final String POINTCUT_PROVIDER_DELETE = "execution(* com.bus.service.Impl.ProviderServiceImpl.removeById(..))";
    private static final String POINTCUT_PROVIDER_SAVE = "execution(* com.bus.service.Impl.ProviderServiceImpl.save(..))";
    private static final String POINTCUT_PROVIDER_BECHEDELETE = "execution(* com.bus.service.Impl.ProviderServiceImpl.removeByIds(..))";
    private static final String CACHE_PROVIDER_PROFIX = "provider:";

    /**
     * 功能描述: <br>
     * 〈〉               查询单个对象切入
     *
     * @Param: [joinPoint]
     * @Return: java.lang.Object
     * @Author: YJQ
     * @Date: 2020-8-22 15:58
     */

    @Around(POINTCUT_PROVIDER_GET)
    public Object cacheProviderGet(ProceedingJoinPoint joinPoint) throws Throwable {
        /*
         * 环绕方法,可自定义目标方法执行的时机
         * @param pjd JoinPoint的子接口,添加了
         *            Object proceed() throws Throwable 执行目标方法
         *            Object proceed(Object[] var1) throws Throwable 传入的新的参数去执行目标方法
         *            两个方法
         * @return 此方法需要返回值,返回值视为目标方法的返回值
         */
//        获取第一个传入目标方法的参数对象，由于是查询则传入目标方法的参数是Integer类型
        Integer object = (Integer) joinPoint.getArgs()[0];
//        先从缓存容器中查看是否缓存了该数据，有则取出，无则去数据库中取出
        Object res1 = CACHE_CONTAINER.get(CACHE_PROVIDER_PROFIX + object);
        if (res1 != null) {
//            缓存中存在该对象，故无需执行目标方法
            log.info("已从缓存里面找到供应商" + CACHE_PROVIDER_PROFIX + object);
            return res1;
        } else {
            //缓存中无此对象，故执行目标方法
            Provider res2 = (Provider) joinPoint.proceed();
//            执行结果存入缓存中
            CACHE_CONTAINER.put(CACHE_PROVIDER_PROFIX + res2.getId(), res2);
            log.info("未从缓存里面找到供应商对象，去数据库查询并放到缓存" + CACHE_PROVIDER_PROFIX + res2.getId());
            return res2;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉       更新方法切入
     *
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-22 16:36
     */
    ///**
    //	 * 更新切入
    //	 *
    //	 * @throws Throwable
    //	 */
    //	@Around(value = POINTCUT_PROVIDER_UPDATE)
    //	public Object cacheProviderUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
    //		// 取出第一个参数
    //		Provider providerVo = (Provider) joinPoint.getArgs()[0];
    //		Boolean isSuccess = (Boolean) joinPoint.proceed();
    //		if (isSuccess) {
    //			Provider provider = (Provider) CACHE_CONTAINER.get(CACHE_PROVIDER_PROFIX + providerVo.getId());
    //			if (null == provider) {
    //				provider = new Provider();
    //			}
    //			BeanUtils.copyProperties(providerVo, provider);
    //			log.info("供应商对象缓存已更新" + CACHE_PROVIDER_PROFIX + providerVo.getId());
    //			CACHE_CONTAINER.put(CACHE_PROVIDER_PROFIX + provider.getId(), provider);
    //		}
    //		return isSuccess;
    //	}
    //
    //	/**
    //	 * 删除切入
    //	 *
    //	 * @throws Throwable
    //	 */
    //	@Around(value = POINTCUT_PROVIDER_DELETE)
    //	public Object cacheProviderDelete(ProceedingJoinPoint joinPoint) throws Throwable {
    //		// 取出第一个参数
    //		Integer id = (Integer) joinPoint.getArgs()[0];
    //		Boolean isSuccess = (Boolean) joinPoint.proceed();
    //		if (isSuccess) {
    //			// 删除缓存
    //			CACHE_CONTAINER.remove(CACHE_PROVIDER_PROFIX + id);
    //			log.info("供应商对象缓存已删除" + CACHE_PROVIDER_PROFIX + id);
    //		}
    //		return isSuccess;
    //	}
    @Around(POINTCUT_PROVIDER_UPDATE)
    public boolean cacheProviderUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
//        获取第一个传入参数
        Provider providerVo = (Provider) joinPoint.getArgs()[0];
//        先从执行目标方法更新数据库，之后更新缓存
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
//            若更新成功，则开始更新缓存
//            首先查看缓存中是否有更新对象的原数据
            Provider provider = (Provider) CACHE_CONTAINER.get(CACHE_PROVIDER_PROFIX + providerVo.getId());
            if (provider == null) {
//                若无元数据，则直接存入更新对象
                provider = new Provider();
            }
            BeanUtils.copyProperties(providerVo, provider);
            log.info("已更新供应商对象" + CACHE_PROVIDER_PROFIX + provider);
            CACHE_CONTAINER.put(CACHE_PROVIDER_PROFIX + provider.getId(), provider);

        }
        return isSuccess;
    }

    /**
     * 功能描述: <br>
     * 〈〉       删除供应商对象切入
     *
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-27 10:02
     */

    @Around(POINTCUT_PROVIDER_DELETE)
    public boolean cacheProviderDelete(ProceedingJoinPoint joinPoint) throws Throwable {
//        获取第一个传入参数
        Integer id = (Integer) joinPoint.getArgs()[0];
//        执行目标方法
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //删除成功，则删除缓存中对应的对象
            log.info("已从缓存中删除供应商对象" + CACHE_PROVIDER_PROFIX + id);
            CACHE_CONTAINER.remove(CACHE_PROVIDER_PROFIX + id);
        }
        return isSuccess;
    }

    /**
     * 功能描述: <br>
     * 〈〉       保存供应商对象切入
     *
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-27 10:02
     */

    @Around(POINTCUT_PROVIDER_SAVE)
    public boolean cacheProviderSave(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        Provider object = (Provider) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            log.info("已更新缓存中供应商对象" + CACHE_PROVIDER_PROFIX + object);
            CACHE_CONTAINER.put(CACHE_PROVIDER_PROFIX + object.getId(), object);
        }
        return res;
    }

    /**
     * 功能描述: <br>
     * 〈〉       批量删除供应商对象切入
     *
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-27 10:02
     */

    @Around(POINTCUT_PROVIDER_BECHEDELETE)
    public boolean cacheBecheDeleteProvider(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        Collection<Serializable> idList = (Collection<Serializable>) joinPoint.getArgs()[0];
//        执行目标方法
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            for (Serializable id : idList) {
                //删除成功，则删除缓存中对应的对象
                log.info("已从缓存中删除供应商对象" + CACHE_CUSTOMER_PROFIX + id);
                CACHE_CONTAINER.remove(CACHE_CUSTOMER_PROFIX + id);
            }
        }
        return isSuccess;
    }


    /**
     * 定义一个切入点表达式,用来确定哪些类需要代理
     * execution(* aopdemo.*.*(..))代表aopdemo包下所有类的所有方法都会被代理
     * 声明切入表达式
     */
    private static final String POINTCUT_GOODS_UPDATE = "execution(* com.bus.service.Impl.GoodsServiceImpl.updateById(..))";
    private static final String POINTCUT_GOODS_SAVE = "execution(* com.bus.service.Impl.GoodsServiceImpl.save(..))";
    private static final String POINTCUT_GOODS_DELETE = "execution(* com.bus.service.Impl.GoodsServiceImpl.removeById(..))";
    private static final String POINTCUT_GOODS_GET = "execution(* com.bus.service.Impl.GoodsServiceImpl.getById(..))";
    private static final String CACHE_GOODS_PROFIX = "goods:";

    /**
     * 功能描述: <br>
     * 〈〉               查询单个对象切入
     *
     * @Param: [joinPoint]
     * @Return: java.lang.Object
     * @Author: YJQ
     * @Date: 2020-8-22 15:58
     */

    @Around(POINTCUT_GOODS_GET)
    public Object cacheGoodsGet(ProceedingJoinPoint joinPoint) throws Throwable {
        /*
         * 环绕方法,可自定义目标方法执行的时机
         * @param pjd JoinPoint的子接口,添加了
         *            Object proceed() throws Throwable 执行目标方法
         *            Object proceed(Object[] var1) throws Throwable 传入的新的参数去执行目标方法
         *            两个方法
         * @return 此方法需要返回值,返回值视为目标方法的返回值
         */
//        获取第一个传入目标方法的参数对象，由于是查询则传入目标方法的参数是Integer类型
        Integer object = (Integer) joinPoint.getArgs()[0];
//        先从缓存容器中查看是否缓存了该数据，有则取出，无则去数据库中取出
        Object res1 = CACHE_CONTAINER.get(CACHE_GOODS_PROFIX + object);
        if (res1 != null) {
//            缓存中存在该对象，故无需执行目标方法
            log.info("已从缓存里面找到商品" + CACHE_GOODS_PROFIX + object);
            return res1;
        } else {
            //缓存中无此对象，故执行目标方法
            Goods res2 = (Goods) joinPoint.proceed();
//            执行结果存入缓存中
            CACHE_CONTAINER.put(CACHE_GOODS_PROFIX + res2.getId(), res2);
            return res2;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉       更新方法切入
     *
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-22 16:36
     */

    @Around(POINTCUT_GOODS_UPDATE)
    public boolean cacheGoodsUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
//        获取第一个传入参数
        Goods goodsVo = (Goods) joinPoint.getArgs()[0];
//        先从执行目标方法更新数据库，之后更新缓存
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
//            若更新成功，则开始更新缓存
//            首先查看缓存中是否有更新对象的原数据
            Goods goods = (Goods) CACHE_CONTAINER.get(CACHE_GOODS_PROFIX + goodsVo.getId());
            if (goods == null) {
//                若无元数据，则直接存入更新对象
                goods = new Goods();
                BeanUtils.copyProperties(goodsVo,goods);
                log.info("已更新商品对象" + CACHE_GOODS_PROFIX + goods);
                CACHE_CONTAINER.put(CACHE_GOODS_PROFIX + goods.getId(), goods);
            }
        }
        return isSuccess;
    }

    /**
     * 功能描述: <br>
     * 〈〉       删除客户对象切入
     *
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-27 10:02
     */

    @Around(POINTCUT_GOODS_DELETE)
    public boolean cacheGoodsDelete(ProceedingJoinPoint joinPoint) throws Throwable {
//        获取第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
//        执行目标方法
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //删除成功，则删除缓存中对应的对象
            log.info("已从缓存中删除商品对象" + CACHE_GOODS_PROFIX + id);
            CACHE_CONTAINER.remove(CACHE_GOODS_PROFIX + id);
        }
        return isSuccess;
    }

   
    /**
     * 功能描述: <br>
     * 〈〉       保存客户对象切入
     *
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-27 10:02
     */

    @Around(POINTCUT_GOODS_SAVE)
    public boolean cacheGoodsSave(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        Goods object = (Goods) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            log.info("已更新缓存中商品对象" + CACHE_PROVIDER_PROFIX + object);
            CACHE_CONTAINER.put(CACHE_PROVIDER_PROFIX + object.getId(), object);
        }
        return res;
    }

}
