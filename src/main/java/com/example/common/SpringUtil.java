package com.example.common;

/**
 * @ProjectName: project-demo
 * @Package: com.example.common
 * @ClassName: SpringUtils
 * @Author: 游佳琪
 * @Description: 获取ApplicationContext对象
 * @Date: 2020-9-10 22:12
 * @Version: 1.0
 */
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext act) throws BeansException {
        applicationContext=act;
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public static <T> T getBean(Class<T> cls) {
        return applicationContext.getBean(cls);
    }

}