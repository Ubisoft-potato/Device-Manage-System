package org.cust.devicemanagesystem.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create by: long
 * description:
 * create time: 2020/1/5 下午9:45
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页拦截器
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
