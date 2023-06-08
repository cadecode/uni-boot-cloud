package top.cadecode.uniboot.common.plugin.cache.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Cade Li
 * @date 2022/2/15
 * @description Redis 版分布式锁
 */
@Component
@RequiredArgsConstructor
public class RedisLock {

    private final StringRedisTemplate redisTemplate;

    private final Map<String, LockContent> contentMap = new ConcurrentHashMap<>();

    // 定时续期任务线程池，合理设置大小
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    /**
     * 阻塞式的获取锁
     *
     * @param name 锁名称
     */
    public void lock(String name) {
        lock(name, "");
    }

    public void lock(String name, String value) {
        if (checkReentrant(name)) {
            storeLock(name, null, true);
            return;
        }
        while (true) {
            if (tryLock0(name, value)) {
                return;
            }
            sleep();
        }
    }

    /**
     * 尝试一次获取锁
     *
     * @param name 锁名称
     * @return 是否获取到
     */
    public boolean tryLock(String name) {
        return tryLock(name, "");
    }

    public boolean tryLock(String name, String value) {
        if (checkReentrant(name)) {
            storeLock(name, null, true);
            return true;
        }
        return tryLock0(name, value);
    }

    /**
     * 尝试在一段时间内阻塞获取锁
     *
     * @param name     锁名称
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     * @return 是否获取到
     */
    public boolean tryLock(String name, long timeout, TimeUnit timeUnit) {
        return tryLock(name, "", timeout, timeUnit);
    }

    public boolean tryLock(String name, String value, long timeout, TimeUnit timeUnit) {
        if (checkReentrant(name)) {
            storeLock(name, null, true);
            return true;
        }
        long totalTime = timeUnit.toMillis(timeout);
        long current = System.currentTimeMillis();
        while (System.currentTimeMillis() - current <= totalTime) {
            if (tryLock0(name, value)) {
                return true;
            }
            sleep();
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param name 锁名称
     */
    public void unlock(String name) {
        if (!checkReentrant(name)) {
            return;
        }
        LockContent lockContent = contentMap.get(name);
        Integer count = lockContent.getCount();
        if (count > 0) {
            // 重入次数减一
            lockContent.setCount(--count);
        }
        // 释放锁
        if (count == 0) {
            // 清除重入记录
            contentMap.remove(name);
            // 停止续期任务
            lockContent.getFuture().cancel(true);
            // 删除 Redis key
            redisTemplate.delete(name);
        }
    }

    /**
     * 清除锁，不检查是不是本线程持有锁，强行删除缓存，应该在确认锁在当前节点持有的时候使用
     * 两种情况，假设当前持有锁的线程为 A 节点线程 A1，其他线程有 A 节点线程 A2，B 节点线程 B1：
     * 1. A 节点的线程清除了 A1 的锁，续期任务正常关闭
     * 2. B1 清除了其他节点持有的锁
     * 2.1 没有继续抢锁，A1 的续期任务会抛出异常后停止，并清理 contentMap
     * 2.2 B1 抢到锁，如果间隔时间没有达到续期任务周期，A1 的续期任务可能会无限执行下去
     * 2.3 A2 抢到锁，storeLock 时会关闭原续期任务
     *
     * @param name 锁名称
     */
    public void clear(String name) {
        LockContent lockContent = contentMap.get(name);
        if (Objects.nonNull(lockContent)) {
            // 清除重入记录
            contentMap.remove(name);
            // 停止续期任务
            lockContent.getFuture().cancel(true);
        }
        // 删除 Redis key
        redisTemplate.delete(name);
    }

    /**
     * 检查重入
     *
     * @param name 锁名称
     * @return 是否重入
     */
    private boolean checkReentrant(String name) {
        if (Objects.isNull(name)) {
            throw new RuntimeException("lock name cannot be null");
        }
        // 判断是否重入
        return Objects.nonNull(contentMap.get(name))
                && contentMap.get(name).getCurrThread() == Thread.currentThread();
    }

    /**
     * 保存重入次数到 contentMap
     *
     * @param name 锁名称
     */
    private void storeLock(String name, ScheduledFuture<?> future, boolean reentrant) {
        LockContent lockContent = contentMap.get(name);
        if (reentrant) {
            // 重入次数加一
            lockContent.setCount(lockContent.getCount() + 1);
            return;
        }
        // 防止有旧锁数据残留
        if (Objects.nonNull(lockContent)) {
            // 停止续期任务
            lockContent.getFuture().cancel(true);
        }
        // 创建新的 LockContent
        lockContent = new LockContent(future, 1, Thread.currentThread());
        contentMap.put(name, lockContent);
    }

    /**
     * 尝试设置 redis key
     *
     * @param name 锁名称
     * @return 是否设置成功
     */
    private boolean tryLock0(String name, String value) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(name, value, 30, TimeUnit.SECONDS);
        if (Objects.equals(success, false)) {
            return false;
        }
        // 设置成功 开启续期任务
        ScheduledFuture<?> future = renewLock(name, value);
        storeLock(name, future, false);
        return true;
    }

    /**
     * 开启锁续期任务
     *
     * @param name 锁名称
     * @return ScheduledFuture
     */
    private ScheduledFuture<?> renewLock(String name, String value) {
        // 有效期设置为 30s，每 15 秒重置
        return executor.scheduleAtFixedRate(() -> {
            Boolean success = redisTemplate.opsForValue().setIfPresent(name, value, 30, TimeUnit.SECONDS);
            if (Objects.equals(success, true)) {
                return;
            }
            // 删除锁
            contentMap.remove(name);
            // 抛出异常停止任务
            throw new RuntimeException("renew lock fail, key is " + name);
        }, 15, 15, TimeUnit.SECONDS);
    }

    /**
     * 休眠一定时间
     */
    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            // 不响应中断
        }
    }

    /**
     * 锁内容
     * 维护续期任务和重入次数
     */
    @Data
    @AllArgsConstructor
    private static class LockContent {
        /**
         * 续期任务
         */
        private ScheduledFuture<?> future;
        /**
         * 重入次数
         */
        private Integer count;

        /**
         * 当前线程
         */
        private Thread currThread;
    }
}
