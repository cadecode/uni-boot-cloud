package com.github.cadecode.uniboot.example.svc.consumer;

import cn.hutool.core.util.ObjectUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RabbitMQ 测试消费
 *
 * @author Cade Li
 * @since 2023/8/17
 */
@Slf4j
@Component
public class RabbitExampleConsumer {

    @RabbitListener(queues = "demo-queue-0", id = "demo-queue-0", ackMode = "AUTO")
    public void demoQueue0(String body, Message message, Channel channel) throws IOException {
        log.info("Received msg:{}", body);
        // 自动模式下，测试重试机制
        if (ObjectUtil.equal(body, "ERROR")) {
            throw new RuntimeException("ERROR");
        }
    }

    @RabbitListener(queues = "#{@'demo-queue-1'.name}", id = "demo-queue-1")
    public void demoQueue1(String body, Message message, Channel channel) throws IOException {
        log.info("Received msg:{}", body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
