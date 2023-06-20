package com.github.cadecode.uniboot.common.plugin.cache.listener;

/**
 * Redis 过期时间处理器接口
 *
 * @author Cade Li
 * @date 2023/6/12
 */
public interface RedisExpiredHandler {

    boolean checkKey(String key);

    void handle(String key);
}
