package top.cadecode.uniboot.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cadecode.uniboot.framework.annotation.ApiFormat;

import java.util.HashMap;

/**
 * Spring Retry 测试
 *
 * @author Cade Li
 * @date 2022/9/28
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = " Spring Retry 测试")
@RestController
@RequestMapping("demo/retry")
public class SpringRetryController {

    /**
     * 声明式
     * 遇到 IllegalArgumentException 时重试
     * 重试 4 次
     * 初次重试间隔 1 秒，后面每次乘以 2
     */
    @Retryable(value = IllegalArgumentException.class, maxAttempts = 4,
            backoff = @Backoff(delay = 1000L, multiplier = 2), recover = "testRecover")
    @ApiOperation("测试 @Retryable 注解")
    @PostMapping("test_retryable_annotation")
    public long testRetryableAnnotation() {
        long round = Math.round(Math.random() * 5);
        if (round <= 1) {
            throw new UnsupportedOperationException("随机数小于等于 1");
        }
        if (round != 4) {
            throw new IllegalArgumentException("随机数不等于 4");
        }
        return round;
    }

    @Recover
    public long testRecover(Throwable t) {
        log.error("recover", t);
        return 9999;
    }

    /**
     * 编程式
     * 重试 4 次
     * 初次重试间隔 1 秒，后面每次乘以 2
     */
    @ApiOperation("测试 RetryTemplate")
    @PostMapping("test_retry_template")
    public long testRetryTemplate() throws Throwable {
        RetryTemplate retryTemplate = new RetryTemplate();
        // 重试规则
        // 针对不同异常定制重试策略
        ExceptionClassifierRetryPolicy exceptionRetryPolicy = new ExceptionClassifierRetryPolicy();
        HashMap<Class<? extends Throwable>, RetryPolicy> policyMap = new HashMap<>();
        // 定制 IllegalArgumentException 的策略
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(4);
        policyMap.put(IllegalArgumentException.class, simpleRetryPolicy);
        exceptionRetryPolicy.setPolicyMap(policyMap);
        retryTemplate.setRetryPolicy(exceptionRetryPolicy);
        // 延迟规则
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000L);
        backOffPolicy.setMultiplier(2);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        Long res = retryTemplate.execute((RetryCallback<Long, Throwable>) context -> {
            long round = Math.round(Math.random() * 5);
            if (round <= 1) {
                throw new UnsupportedOperationException("随机数小于等于 1");
            }
            if (round != 4) {
                throw new IllegalArgumentException("随机数不等于 4");
            }
            return round;
        });

        return res;
    }
}
