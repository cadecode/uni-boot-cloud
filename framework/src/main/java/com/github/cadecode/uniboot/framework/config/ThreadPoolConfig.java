package com.github.cadecode.uniboot.framework.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.dynamictp.core.DtpRegistry;
import org.dromara.dynamictp.core.spring.DtpLifecycle;
import org.dromara.dynamictp.core.support.ExecutorAdapter;
import org.dromara.dynamictp.core.support.ExecutorWrapper;
import org.dromara.dynamictp.core.thread.DtpExecutor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.Lifecycle;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


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

    /**
     * Spring 定时任务线程池 @Scheduled
     */
    @Bean(name = "taskScheduler")
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

    /**
     * spring 异步任务线程池 @Async
     */
    @Bean
    public AsyncConfigurer asyncConfigurer(DtpExecutor asyncExecutor) {
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

    /**
     * 定时任务线程池需要在普通线程池之前关闭，因为定时任务可能向其他线程池提交任务
     * 注：如果项目中存在普通线程池也向定时任务线程池中提交任务的情况，可能需要根据依赖关系细化定制关闭顺序
     */
    @Bean
    public Lifecycle taskSchedulerLifeCycle(ThreadPoolTaskScheduler taskScheduler) {
        return new TaskSchedulerLifeCycle(taskScheduler);
    }

    /**
     * BeanDefinitionRegistryPostProcessor
     * 覆盖 DynamicTp DtpLifCycle getPhase，定制关闭顺序，实现优雅停机
     */
    @Bean
    public BeanDefinitionRegistryPostProcessor executorLifecycleRegistryPostProcessor() {
        return new BeanDefinitionRegistryPostProcessor() {
            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                // 删除 DynamicTp bean dtpLifecycle
                registry.removeBeanDefinition("dtpLifecycle");
                // 注册自定义的 Lifecycle
                GenericBeanDefinition definition = new GenericBeanDefinition();
                definition.setBeanClass(ExecutorLifeCycle.class);
                registry.registerBeanDefinition("dtpLifecycle", definition);
            }

            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

            }
        };
    }

    /**
     * 判断线程池是否还有正则执行的任务
     */
    public static boolean isPoolActive(Executor executor) {
        // 兼容 DtpExecutor
        if (executor instanceof ExecutorAdapter) {
            ExecutorAdapter<?> adapter = (ExecutorAdapter<?>) executor;
            return adapter.getActiveCount() == 0;
        } else if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            return threadPoolExecutor.getActiveCount() == 0;
        } else if (executor instanceof ForkJoinPool) {
            ForkJoinPool forkJoinPool = (ForkJoinPool) executor;
            return forkJoinPool.getActiveThreadCount() == 0
                    && forkJoinPool.getRunningThreadCount() == 0
                    && forkJoinPool.getQueuedTaskCount() == 0
                    && forkJoinPool.getQueuedSubmissionCount() == 0;
        }
        return true;
    }

    @Slf4j
    public static class TaskSchedulerLifeCycle implements SmartLifecycle {
        private final AtomicBoolean running = new AtomicBoolean(false);

        private final ThreadPoolTaskScheduler taskScheduler;

        public TaskSchedulerLifeCycle(ThreadPoolTaskScheduler taskScheduler) {
            this.taskScheduler = taskScheduler;
        }

        @Override
        public void start() {
            running.compareAndSet(false, true);
        }

        @Override
        public void stop() {
            log.info("LifeCycle preparing to stop taskScheduler");
            if (this.running.compareAndSet(true, false)) {
                taskScheduler.shutdown();
            }
        }

        @Override
        public boolean isRunning() {
            return running.get();
        }
    }

    @Slf4j
    public static class ExecutorLifeCycle extends DtpLifecycle {
        @Override
        public int getPhase() {
            // webServer、taskScheduler、mq 的 phase 是 DEFAULT_PHASE
            // 在 webServer、taskScheduler、mq 关闭之后再关闭
            return SmartLifecycle.DEFAULT_PHASE - 1;
        }

        @Override
        public void stop() {
            log.info("LifeCycle preparing to stop all thread pool");
            // 由于存在线程池互相提交现象，不能直接全部 shutdown
            List<? extends ExecutorAdapter<?>> executorList = DtpRegistry.listAllExecutors().values().stream()
                    .map(ExecutorWrapper::getExecutor).collect(Collectors.toList());
            // 打乱重复校验 5 次
            for (int i = 0; i < 5; ) {
                Collections.shuffle(executorList);
                if (executorList.stream().allMatch(ThreadPoolConfig::isPoolActive)) {
                    i++;
                    log.info("LifeCycle check all thread pool completed, check order is {}", i);
                } else {
                    i = 0;
                    log.info("LifeCycle check not all thread pool completed, wait 1s");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ignored) {
                        //
                    }
                }
            }
            super.stop();
        }
    }
}
