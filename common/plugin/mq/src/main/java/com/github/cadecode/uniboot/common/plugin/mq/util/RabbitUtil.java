package com.github.cadecode.uniboot.common.plugin.mq.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
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
        return send(exchangeName, routingKey, message, msg -> msg, null);
    }

    public static String send(String exchangeName, String routingKey, Object message, MessagePostProcessor postProcessor) {
        return send(exchangeName, routingKey, message, postProcessor, null);
    }

    public static String send(String exchangeName, String routingKey, Object message, MessagePostProcessor postProcessor,
                              CorrelationData correlationData) {
        // 不存在消息 id 时构造新的 correlationData
        if (ObjUtil.isNull(correlationData) || ObjUtil.isNull(correlationData.getId())) {
            correlationData = geneCorrelationData();
        }
        RABBIT_TEMPLATE.convertAndSend(exchangeName, routingKey, message, postProcessor, correlationData);
        return correlationData.getId();
    }

    public static String sendDelay(String exchangeName, String routingKey, Object message, Integer delayTime) {
        return sendDelay(exchangeName, routingKey, message, delayTime, msg -> msg, null);
    }

    public static String sendDelay(String exchangeName, String routingKey, Object message, Integer delayTime,
                                   MessagePostProcessor postProcessor) {
        return sendDelay(exchangeName, routingKey, message, delayTime, postProcessor, null);
    }

    public static String sendDelay(String exchangeName, String routingKey, Object message, Integer delayTime,
                                   MessagePostProcessor postProcessor, CorrelationData correlationData) {
        CorrelationData currCorrelationData;
        if (ObjUtil.isNull(correlationData) || ObjUtil.isNull(correlationData.getId())) {
            currCorrelationData = geneCorrelationData();
        } else {
            currCorrelationData = correlationData;
        }
        RABBIT_TEMPLATE.convertAndSend(exchangeName, routingKey, message, msg -> {
            // 注入 delay 时间
            msg.getMessageProperties().setDelay(delayTime);
            postProcessor.postProcessMessage(msg, currCorrelationData);
            return msg;
        }, currCorrelationData);
        return currCorrelationData.getId();
    }

    public static CorrelationData geneCorrelationData() {
        // 有 hutool 雪花算法生成 id
        String msgId = String.valueOf(IdUtil.getSnowflakeNextIdStr());
        return new CorrelationData(msgId);
    }

    @Override
    public void afterPropertiesSet() {
        RABBIT_TEMPLATE = rabbitTemplate;
    }
}
