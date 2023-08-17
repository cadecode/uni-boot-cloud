package com.github.cadecode.uniboot.example.svc.controller;

import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * MQ 测试接口
 *
 * @author Cade Li
 * @since 2023/8/16
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "MQ 测试")
@RestController
@RequestMapping("demo/mq")
public class MqExampleController {

    private final RabbitTemplate rabbitTemplate;

    @ApiOperation("发送 delay 消息")
    @GetMapping("send_delay")
    public boolean sendDelay(@RequestParam String exc, @RequestParam String routingKey,
                             @RequestParam String msg, @RequestParam Integer ms) {
        rabbitTemplate.convertAndSend(exc, routingKey, msg, message -> {
            message.getMessageProperties().setDelay(ms);
            return message;
        });
        return true;
    }
}

