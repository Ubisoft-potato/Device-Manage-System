package org.cust.devicemanagesystem.config;


import org.cust.devicemanagesystem.config.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegister() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setName("CorsFilter");
        registrationBean.setFilter(new CorsFilter());
        registrationBean.setUrlPatterns(Collections.singletonList("/**"));
        return registrationBean;
    }
}
