package com.github.cadecode.uniboot.common.plugin.concurrent.util;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

/**
 * Transmittable 工具类
 *
 * @author Cade Li
 * @since 2023/6/9
 */
public class TransmitUtil {

    // 包装线程池或者 Runnable/Callable

    public static Executor wrap(Executor executor) {
        return TtlExecutors.getTtlExecutor(executor);
    }

    public static TtlRunnable wrap(Runnable runnable) {
        return TtlRunnable.get(runnable);
    }

    public static <T> TtlCallable<T> wrap(Callable<T> callable) {
        return TtlCallable.get(callable);
    }
}
