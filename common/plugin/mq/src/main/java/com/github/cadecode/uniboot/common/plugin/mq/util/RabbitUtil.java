package com.github.cadecode.uniboot.common.plugin.mq.util;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ 工具类
 *
 * @author Cade Li
 * @since 2023/8/18
 */
@RequiredArgsConstructor
@Component
public class RabbitUtil implements InitializingBean {

    private static RabbitTemplate RABBIT_TEMPLATE;

    private final RabbitTemplate rabbitTemplate;

    public static RabbitTemplate template() {
        return RABBIT_TEMPLATE;
    }

    public static String send(String exchangeName, String routingKey, Object message) {
        return send(exchangeName, routingKey, message, msg -> msg);
    }

    public static String send(String exchangeName, String routingKey, Object message, MessagePostProcessor postProcessor) {
        CorrelationData correlationData = geneCorrelationData(message);
        RABBIT_TEMPLATE.convertAndSend(exchangeName, routingKey, message, postProcessor, correlationData);
        return correlationData.getId();
    }

    public static String sendDelay(String exchangeName, String routingKey, Object message, Integer delayTime) {
        return sendDelay(exchangeName, routingKey, message, delayTime, msg -> msg);
    }

    public static String sendDelay(String exchangeName, String routingKey, Object message, Integer delayTime, MessagePostProcessor postProcessor) {
        CorrelationData correlationData = geneCorrelationData(message);
        RABBIT_TEMPLATE.convertAndSend(exchangeName, routingKey, message, msg -> {
            // 注入 delay 时间
            msg.getMessageProperties().setDelay(delayTime);
            postProcessor.postProcessMessage(msg, correlationData);
            return msg;
        }, correlationData);
        return correlationData.getId();
    }

    public static CorrelationData geneCorrelationData(Object message) {
        // 有 hutool 雪花算法生成 id
        String msgId = String.valueOf(IdUtil.getSnowflakeNextIdStr());
        return new CorrelationData(msgId);
    }

    @Override
    public void afterPropertiesSet() {
        RABBIT_TEMPLATE = rabbitTemplate;
    }
}
