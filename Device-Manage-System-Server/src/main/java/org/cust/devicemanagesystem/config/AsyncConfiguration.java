package org.cust.devicemanagesystem.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName AsyncConfiguration @Description TODO @Author long @Date 2020/5/25 22:40 @Version 1.0
 */
@Configuration
@EnableAsync
@EnableWebMvc
@Slf4j
public class AsyncConfiguration implements WebMvcConfigurer {

  @Bean(name = "taskExecutor")
  public AsyncTaskExecutor getAsyncExecutor() {
    log.debug("Creating Async Task Executor");
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(10);
    executor.setQueueCapacity(25);
    executor.setThreadNamePrefix("Async-Service-");
    return executor;
  }

  @Override
  public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    configurer.setDefaultTimeout(60 * 1000L).setTaskExecutor(getAsyncExecutor());
  }
}
