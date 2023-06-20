package com.github.cadecode.uniboot.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * Spring Retry 配置类
 *
 * @author Cade Li
 * @date 2022/9/28
 */
@EnableRetry
@Configuration
public class RetryConfig {

    /**
     * 重试模板，重试三次
     */
    @Primary
    @Bean
    public RetryTemplate retryTemplate3Times() {
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        // 延迟规则
        // 初次重试间隔 1 秒，后面每次乘以 2
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000L);
        backOffPolicy.setMultiplier(2);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }

}
