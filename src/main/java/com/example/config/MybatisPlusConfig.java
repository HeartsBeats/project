package com.example.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ProjectName: project-demo
 * @Package: com.example.config
 * @ClassName: MybatisPlusConfig
 * @Author: 游佳琪
 * @Description: MybatisPlus中一些组件的配置类
 * @Date: 2020-8-17 20:45
 * @Version: 1.0
 */

@EnableTransactionManagement
/**@ConditionalOnClass注解作用： 某个class位于类路径上，才会实例化一个Bean。即判断当前classpath下是否存在指定类，
 * 若是则将当前的配置装载入spring容器
 */
@ConditionalOnClass(value = {PaginationInterceptor.class})
@Configuration
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
