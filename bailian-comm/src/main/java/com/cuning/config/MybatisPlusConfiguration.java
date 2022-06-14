package com.cuning.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class MybatisPlusConfiguration {

    /**
     * 分页插件     */
    @Bean
    public MybatisPlusInterceptor paginationInnerInterceptor() {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //声明针对分页的拦截器
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor();
        //声明数据库类型
        pageInterceptor.setDbType(DbType.MYSQL);
        //添加到拦截器链中
        interceptor.addInnerInterceptor(pageInterceptor);
        return interceptor;
    }
}

