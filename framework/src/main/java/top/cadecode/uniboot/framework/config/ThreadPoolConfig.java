package top.cadecode.uniboot.framework.config;

import com.dtp.core.thread.DtpExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * 线程池设置
 *
 * @author Cade Li
 * @date 2023/3/15
 */
@Slf4j
@Configuration
public class ThreadPoolConfig {

    /**
     * Spring 定时任务线程池
     */
    @Bean(name = "taskScheduler", destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(6);
        scheduler.setThreadNamePrefix("taskScheduler");
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setAwaitTerminationSeconds(5);
        return scheduler;
    }


    @Slf4j
    @RequiredArgsConstructor
    @Configuration
    public static class ExecutorConfig implements AsyncConfigurer, SchedulingConfigurer {

        private final DtpExecutor asyncExecutor;
        private final ThreadPoolTaskScheduler taskScheduler;

        @Override
        public Executor getAsyncExecutor() {
            return asyncExecutor;
        }

        @Override
        public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
            return (throwable, method, objects) -> {
                log.error("异步任务执行出现异常, message {}, method {}, params {}",
                        throwable, method, objects);
            };

        }

        @Override
        public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
            taskRegistrar.setTaskScheduler(taskScheduler);
        }
    }
}
