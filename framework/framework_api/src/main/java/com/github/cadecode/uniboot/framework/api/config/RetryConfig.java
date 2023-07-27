package com.github.cadecode.uniboot.framework.api.config;

import com.github.cadecode.uniboot.framework.api.exception.RetryableException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.EnableRetry;
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
     * 默认重试模板，所有异常类型
     * 重试 5 次，间隔 1 秒，每次间隔时间 x2
     */
    @Primary
    @Bean
    public RetryTemplate retryTemplate() {
        /*
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000L);
        backOffPolicy.setMaxInterval(10000L);
        backOffPolicy.setMultiplier(2);
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        */

        // 使用 builder 模式代替以上代码
        return RetryTemplate.builder()
                .maxAttempts(5)
                .retryOn(Exception.class)
                .exponentialBackoff(1000L, 2, 30000L)
                .build();
    }

    /**
     * 指定异常类型的重试模板，只重试异常类型或异常 cause 为 RetryableException
     * 通过抛出 RetryableException 实现自定义控制重试逻辑
     * 重试 5 次，间隔 1 秒，每次间隔时间 x2
     */
    @Bean
    public RetryTemplate retryTemplateOnRetryable() {
        return RetryTemplate.builder()
                .maxAttempts(5)
                .retryOn(RetryableException.class)
                // 开启 cause 穿透，可根据 cause 判断
                .traversingCauses()
                .exponentialBackoff(1000L, 2, 30000L)
                .build();
    }
}
