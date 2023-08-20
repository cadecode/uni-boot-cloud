package com.github.cadecode.uniboot.common.plugin.cache.l2cache.sync;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.common.plugin.cache.l2cache.DLCacheProperties;
import com.github.cadecode.uniboot.common.plugin.cache.l2cache.cache.DLCacheManager;
import com.github.cadecode.uniboot.common.plugin.cache.listener.RedisMessageListener;
import com.github.cadecode.uniboot.common.plugin.cache.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 二级缓存刷新监听器
 *
 * @author Cade Li
 * @date 2023/6/15
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DLCacheRefreshListener extends RedisMessageListener {

    public static final ConcurrentHashSet<DLCacheRefreshMsg> SELF_MSG_MAP = new ConcurrentHashSet<>();

    private final DLCacheManager dlCacheManager;
    private final DLCacheProperties cacheProperties;

    @Override
    public List<Topic> topics() {
        String syncTopic = cacheProperties.getRemote().getSyncTopic();
        return Collections.singletonList(new PatternTopic(syncTopic));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 序列化出刷新消息
        DLCacheRefreshMsg refreshMsg = (DLCacheRefreshMsg) RedisUtil.getTemplate().getValueSerializer().deserialize(message.getBody());
        if (ObjUtil.isNull(refreshMsg)) {
            return;
        }
        // 判断是不是自身节点发出
        if (SELF_MSG_MAP.contains(refreshMsg)) {
            SELF_MSG_MAP.remove(refreshMsg);
            return;
        }
        log.debug("DLCache refresh local, cache name:{}, key:{}", refreshMsg.getCacheName(), refreshMsg.getKey());
        // 清理本地缓存
        dlCacheManager.getCache(refreshMsg.getCacheName()).clearLocal(refreshMsg.getKey());
    }
}
