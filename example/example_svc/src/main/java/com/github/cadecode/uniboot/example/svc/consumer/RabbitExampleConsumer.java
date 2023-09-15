package com.github.cadecode.uniboot.example.svc.consumer;

import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.example.svc.bean.data.ExampleMsgDo;
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

    @RabbitListener(queues = "example-delay-queue-0",
            id = "example-delay-queue-0",
            ackMode = "AUTO")
    public void exampleDelayQueue0(String body, Message message, Channel channel) throws IOException {
        log.info("Received msg:{}", body);
    }

    /**
     * 测试 SpEL 获取 queue name
     */
    @RabbitListener(containerFactory = "default",
            queues = "#{@'default_example-delay-queue-1'.name}",
            id = "example-delay-queue-1")
    public void exampleDelayQueue1(String body, Message message, Channel channel) throws IOException {
        log.info("Received msg:{}", body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    @RabbitListener(containerFactory = "default",
            queues = "example-biz-queue-0",
            id = "example-biz-queue-0",
            ackMode = "AUTO")
    public void exampleBizQueue0(String body, Message message, Channel channel) throws IOException {
        log.info("Received msg:{}", body);
        // 自动模式下，测试重试机制
        if (ObjUtil.equal(body, "ERROR")) {
            throw new RuntimeException("ERROR");
        }
    }

    /**
     * 测试用对象获取消息
     */
    @RabbitListener(containerFactory = "default",
            queues = "example-biz-queue-1",
            id = "example-biz2-queue-1",
            ackMode = "AUTO")
    public void exampleBizQueue1(ExampleMsgDo msgDo, Message message, Channel channel) throws IOException {
        log.info("Received msg:{}", msgDo);
    }
}
