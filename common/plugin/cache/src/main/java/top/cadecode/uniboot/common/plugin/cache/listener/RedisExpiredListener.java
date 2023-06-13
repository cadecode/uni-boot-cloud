package top.cadecode.uniboot.common.plugin.cache.listener;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Redis key 过期监听器
 *
 * @author Cade Li
 * @date 2023/6/12
 */
@Slf4j
@Component
@ConditionalOnBean(RedisExpiredHandler.class)
public class RedisExpiredListener extends KeyExpirationEventMessageListener {

    private final List<RedisExpiredHandler> expiredHandlers;

    public RedisExpiredListener(RedisMessageListenerContainer listenerContainer,
                                List<RedisExpiredHandler> expiredHandlers) {
        super(listenerContainer);
        this.expiredHandlers = expiredHandlers;
    }

    public void onMessage(Message message, byte[] pattern) {
        String key = new String(message.getBody());
        // 根据 order 注解和 checkKey 方法，获取第一个合适的处理器
        Optional<RedisExpiredHandler> handlerOpt = expiredHandlers.stream()
                .filter(o -> o.checkKey(key))
                .sorted(Comparator.comparing(o -> {
                    Order order = o.getClass().getAnnotation(Order.class);
                    return ObjectUtil.defaultIfNull(order, order::value, 0);
                }).reversed())
                .findAny();
        if (!handlerOpt.isPresent()) {
            return;
        }
        RedisExpiredHandler handler = handlerOpt.get();
        log.info("Redis expired listener find key {}, handler {}", key, handler.getClass().getName());
        try {
            handler.handle(key);
        } catch (Exception e) {
            // 兜底异常处理
            log.error("Redis expired listener handler failed, key {}, handler {}", key, handler.getClass().getName(), e);
        }
    }
}
