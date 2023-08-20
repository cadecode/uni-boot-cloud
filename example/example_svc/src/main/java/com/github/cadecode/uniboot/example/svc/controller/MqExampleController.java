package com.github.cadecode.uniboot.example.svc.controller;

import com.github.cadecode.uniboot.common.plugin.mq.util.RabbitUtil;
import com.github.cadecode.uniboot.example.svc.bean.data.ExampleMsgDo;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @ApiOperation("发送 delay 消息")
    @GetMapping("send_delay")
    public boolean sendDelay(@RequestParam String exchange, @RequestParam String routingKey, @RequestParam Integer ms) {
        RabbitUtil.sendDelay(exchange, routingKey, "Test delay msg", ms);
        return true;
    }

    @ApiOperation("发送字符串消息")
    @GetMapping("send_str")
    public boolean sendStr(@RequestParam String exchange, @RequestParam String routingKey) {
        RabbitUtil.send(exchange, routingKey, "Test str msg");
        return true;
    }

    @ApiOperation("发送对象消息")
    @GetMapping("send_obj")
    public boolean sendObj(@RequestParam String exchange, @RequestParam String routingKey) {
        ExampleMsgDo msgDo = new ExampleMsgDo("Test key", "Test name");
        RabbitUtil.send(exchange, routingKey, msgDo);
        return true;
    }
}

