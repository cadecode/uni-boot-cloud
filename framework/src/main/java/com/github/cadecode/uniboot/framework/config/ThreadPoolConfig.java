package com.github.cadecode.uniboot.framework.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.dynamictp.core.thread.DtpExecutor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;


/**
 * 线程池设置
 *
 * @author Cade Li
 * @date 2023/3/15
 */
@Slf4j
@RequiredArgsConstructor
@EnableAsync
@EnableScheduling
@Configuration
public class ThreadPoolConfig {

    private final DtpExecutor asyncExecutor;

    /**
     * Spring 定时任务线程池
     */
    @Bean(name = "taskScheduler", destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        scheduler.setThreadNamePrefix("taskScheduler-");
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setAwaitTerminationSeconds(300);
        scheduler.setErrorHandler(throwable -> {
            log.error("Scheduled task execute fail,", throwable);
        });
        return scheduler;
    }

    @Bean
    public SchedulingConfigurer schedulingConfigurer(ThreadPoolTaskScheduler taskScheduler) {
        return taskRegistrar -> taskRegistrar.setTaskScheduler(taskScheduler);
    }

    @Bean
    public AsyncConfigurer asyncConfigurer() {
        return new AsyncConfigurer() {
            @Override
            public Executor getAsyncExecutor() {
                return asyncExecutor;
            }

            @Override
            public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
                return (throwable, method, objects) -> {
                    log.error("Async task execute fail, method {}, params {}", method, objects, throwable);
                };
            }
        };
    }
}
