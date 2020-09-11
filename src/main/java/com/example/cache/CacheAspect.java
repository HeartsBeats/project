package com.example.cache;


import com.example.entity.Dept;
import com.example.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: project-demo
 * @Package: com.example.cache
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
public class CacheAspect {


    /**
     * 日志出处
     */
    private Log log = LogFactory.getLog(CacheAspect.class);

    //声明一个缓存容器
    private Map<String, Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;

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
    private static final String POINTCUT_DEPT_UPDATE = "execution(* com.example.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_ADD = "execution(* com.example.service.impl.DeptServiceImpl.save(..))";
    private static final String POINTCUT_DEPT_DELETE = "execution(* com.example.service.impl.DeptServiceImpl.removeById(..))";
    private static final String POINTCUT_DEPT_GET = "execution(* com.example.service.impl.DeptServiceImpl.getById(..))";

    private static final String CACHE_DEPT_PROFIX = "dept:";

    /**
     * 功能描述: <br>
     * 〈〉               查询单个对象切入
     *
     * @Param: [joinPoint]
     * @Return: java.lang.Object
     * @Author: YJQ
     * @Date: 2020-8-22 15:58
     */

    @Around(POINTCUT_DEPT_GET)
    public Object cacheDeptGet(ProceedingJoinPoint joinPoint) throws Throwable {
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
        Object res1 = CACHE_CONTAINER.get(CACHE_DEPT_PROFIX + object);
        if (res1 != null) {
//            缓存中存在该对象，故无需执行目标方法
            log.info("已从缓存里面找到部门对象" + CACHE_DEPT_PROFIX + object);
            return res1;
        } else {
            //缓存中无此对象，故执行目标方法
            Dept res2 = (Dept) joinPoint.proceed();
//            执行结果存入缓存中
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX + res2.getId(), res2);
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

    @Around(POINTCUT_DEPT_UPDATE)
    public boolean cacheDeptUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
//        获取第一个传入参数
        Dept deptVo = (Dept) joinPoint.getArgs()[0];
//        先从执行目标方法更新数据库，之后更新缓存
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
//            若更新成功，则开始更新缓存
//            首先查看缓存中是否有更新对象的原数据
            Dept dept = (Dept) CACHE_CONTAINER.get(CACHE_DEPT_PROFIX + deptVo.getId());
            if (dept == null) {
//                若无元数据，则直接存入更新对象
                dept = new Dept();
                BeanUtils.copyProperties(deptVo, dept);
                log.info("已更新部门对象" + CACHE_DEPT_PROFIX +dept);
                CACHE_CONTAINER.put(CACHE_DEPT_PROFIX + dept.getId(), dept);
            }
        }
        return isSuccess;
    }

    /**
     * 功能描述: <br>
     * 〈〉       删除部门对象切入
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-27 10:02
     */

    @Around(POINTCUT_DEPT_DELETE)
    public boolean cacheDeptDelete(ProceedingJoinPoint joinPoint) throws Throwable {
//        获取第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
//        执行目标方法
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //删除成功，则删除缓存中对应的对象
            log.info("已从缓存中删除部门对象" + CACHE_DEPT_PROFIX + id);
            CACHE_CONTAINER.remove(CACHE_DEPT_PROFIX + id);
        }
        return isSuccess;
    }

    /*****************************用户切入*****************************************
    /**
     * 定义一个切入点表达式,用来确定哪些类需要代理
     * execution(* aopdemo.*.*(..))代表aopdemo包下所有类的所有方法都会被代理
     * 声明切入表达式
     */
    private static final String POINTCUT_USER_UPDATE = "execution(* com.example.service.impl.UserServiceImpl.updateById(..))";
    private static final String POINTCUT_USER_GET = "execution(* com.example.service.impl.UserServiceImpl.getById(..))";
    private static final String POINTCUT_USER_DELETE = "execution(* com.example.service.impl.UserServiceImpl.removeById(..))";
    private static final String POINTCUT_USER_SAVE = "execution(* com.example.service.impl.UserServiceImpl.save(..))";

    private static final String CACHE_USER_PROFIX = "user:";

    /**
     * 功能描述: <br>
     * 〈〉               查询单个对象切入
     *
     * @Param: [joinPoint]
     * @Return: java.lang.Object
     * @Author: YJQ
     * @Date: 2020-8-22 15:58
     */

    @Around(POINTCUT_USER_GET)
    public Object cacheUserGet(ProceedingJoinPoint joinPoint) throws Throwable {
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
        Object res1 = CACHE_CONTAINER.get(CACHE_USER_PROFIX + object);
        if (res1 != null) {
//            缓存中存在该对象，故无需执行目标方法
            log.info("已从缓存里面找到用户" + CACHE_USER_PROFIX + object);
            return res1;
        } else {
            //缓存中无此对象，故执行目标方法
            User res2 = (User) joinPoint.proceed();
//            执行结果存入缓存中
            CACHE_CONTAINER.put(CACHE_USER_PROFIX + res2.getId(), res2);
            log.info("未从缓存里面找到用户对象，去数据库查询并放到缓存"+CACHE_USER_PROFIX+res2.getId());
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
    //	@Around(value = POINTCUT_USER_UPDATE)
    //	public Object cacheUserUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
    //		// 取出第一个参数
    //		User userVo = (User) joinPoint.getArgs()[0];
    //		Boolean isSuccess = (Boolean) joinPoint.proceed();
    //		if (isSuccess) {
    //			User user = (User) CACHE_CONTAINER.get(CACHE_USER_PROFIX + userVo.getId());
    //			if (null == user) {
    //				user = new User();
    //			}
    //			BeanUtils.copyProperties(userVo, user);
    //			log.info("用户对象缓存已更新" + CACHE_USER_PROFIX + userVo.getId());
    //			CACHE_CONTAINER.put(CACHE_USER_PROFIX + user.getId(), user);
    //		}
    //		return isSuccess;
    //	}
    //
    //	/**
    //	 * 删除切入
    //	 *
    //	 * @throws Throwable
    //	 */
    //	@Around(value = POINTCUT_USER_DELETE)
    //	public Object cacheUserDelete(ProceedingJoinPoint joinPoint) throws Throwable {
    //		// 取出第一个参数
    //		Integer id = (Integer) joinPoint.getArgs()[0];
    //		Boolean isSuccess = (Boolean) joinPoint.proceed();
    //		if (isSuccess) {
    //			// 删除缓存
    //			CACHE_CONTAINER.remove(CACHE_USER_PROFIX + id);
    //			log.info("用户对象缓存已删除" + CACHE_USER_PROFIX + id);
    //		}
    //		return isSuccess;
    //	}

    @Around(POINTCUT_USER_UPDATE)
    public boolean cacheUserUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
//        获取第一个传入参数
         User userVo = (User) joinPoint.getArgs()[0];
//        先从执行目标方法更新数据库，之后更新缓存
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
//            若更新成功，则开始更新缓存
//            首先查看缓存中是否有更新对象的原数据
            User user = (User) CACHE_CONTAINER.get(CACHE_USER_PROFIX + userVo.getId());
            if (user == null) {
//                若无元数据，则直接存入更新对象
                user = new User();
            }
                BeanUtils.copyProperties(userVo, user);
                log.info("已更新用户对象" + CACHE_USER_PROFIX +user);
                CACHE_CONTAINER.put(CACHE_USER_PROFIX + user.getId(), user);

        }
        return isSuccess;
    }

    /**
     * 功能描述: <br>
     * 〈〉       删除用户对象切入
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-27 10:02
     */

    @Around(POINTCUT_USER_DELETE)
    public boolean cacheUserDelete(ProceedingJoinPoint joinPoint) throws Throwable {
//        获取第一个传入参数
       Integer id = (Integer) joinPoint.getArgs()[0];
//        执行目标方法
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //删除成功，则删除缓存中对应的对象
            log.info("已从缓存中删除用户对象" + CACHE_USER_PROFIX + id);
            CACHE_CONTAINER.remove(CACHE_USER_PROFIX + id);
        }
        return isSuccess;
    }
    /**
     * 功能描述: <br>
     * 〈〉       保存用户对象切入
     * @Param: [joinPoint]
     * @Return: boolean
     * @Author: YJQ
     * @Date: 2020-8-27 10:02
     */

    @Around(POINTCUT_USER_SAVE)
    public boolean cacheUserSave(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        User object = (User) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            log.info("已更新缓存中用户对象" + CACHE_USER_PROFIX + object);
            CACHE_CONTAINER.put(CACHE_USER_PROFIX + object.getId(), object);
        }
        return res;
    }

}
