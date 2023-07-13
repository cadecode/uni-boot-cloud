package com.github.cadecode.uniboot.common.plugin.cache.listener;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Redis key 过期监听器
 *
 * @author Cade Li
 * @date 2023/6/12
 */
@Slf4j
@Component
@ConditionalOnBean(RedisExpiredHandler.class)
public class RedisExpiredListener extends RedisMessageListener {

    private final List<RedisExpiredHandler> expiredHandlers;

    /**
     * 处理器按 key 缓存
     */
    private final ConcurrentHashMap<String, RedisExpiredHandler> HANDLER_MAP = new ConcurrentHashMap<>();


    public RedisExpiredListener(List<RedisExpiredHandler> expiredHandlers) {
        this.expiredHandlers = expiredHandlers;
    }

    @Override
    public List<Topic> topics() {
        return Collections.singletonList(new PatternTopic("__keyevent@*__:expired"));
    }

    public void onMessage(Message message, byte[] pattern) {
        String key = new String(message.getBody());
        RedisExpiredHandler handler;
        if (HANDLER_MAP.containsKey(key)) {
            handler = HANDLER_MAP.get(key);
        } else {
            // 根据 order 注解和 checkKey 方法，获取第一个合适的处理器
            Optional<RedisExpiredHandler> handlerOpt = expiredHandlers.stream()
                    .filter(o -> o.checkKey(key))
                    .sorted(Comparator.comparing(o -> {
                        Order order = o.getClass().getAnnotation(Order.class);
                        return ObjectUtil.defaultIfNull(order, Order::value, 0);
                    }).reversed())
                    .findAny();
            if (!handlerOpt.isPresent()) {
                return;
            }
            handler = handlerOpt.get();
            HANDLER_MAP.put(key, handler);
        }
        log.info("Redis expired listener find key {}, handler {}", key, handler.getClass().getName());
        try {
            handler.handle(key);
        } catch (Exception e) {
            // 兜底异常处理
            log.error("Redis expired listener handler failed, key {}, handler {}", key, handler.getClass().getName(), e);
        }
    }
}
