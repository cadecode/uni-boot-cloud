package com.github.cadecode.uniboot.common.plugin.cache.l2cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;


/**
 * 二级缓存配置项
 *
 * @author Cade Li
 * @date 2023/6/15
 */
@Data
@ConfigurationProperties(prefix = "uni-boot.cache.dl")
public class DLCacheProperties {

    /**
     * 是否存储 null 值
     */
    private boolean allowNullValues = true;

    /**
     * 过期时间，为 0 表示不过期，默认 30 分钟
     * 单位：毫秒
     */
    private long defaultExpiration = 30 * 60 * 1000;

    /**
     * 针对 cacheName 设置过期时间，为 0 表示不过期
     * 单位：毫秒
     */
    private Map<String, Long> cacheExpirationMap;

    /**
     * 本地缓存 caffeine 配置
     */
    private LocalConfig local = new LocalConfig();

    /**
     * 远程缓存 redis 配置
     */
    private RemoteConfig remote = new RemoteConfig();


    @Data
    public static class LocalConfig {

        /**
         * 初始化大小，为 0 表示默认
         */
        private int initialCapacity;

        /**
         * 最大缓存个数，为 0 表示默认
         * 默认最多 5 万条
         */
        private long maximumSize = 10000L;
    }

    @Data
    public static class RemoteConfig {

        /**
         * Redis pub/sub 缓存刷新通知主题
         */
        private String syncTopic = "cache:dl:refresh:topic";
    }
}
