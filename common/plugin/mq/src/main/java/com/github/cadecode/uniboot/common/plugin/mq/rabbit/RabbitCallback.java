package com.github.cadecode.uniboot.common.plugin.mq.rabbit;

import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.common.plugin.mq.consts.RabbitConst;
import com.github.cadecode.uniboot.common.plugin.mq.handler.AbstractTxMsgHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnsCallback;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RabbitMQ 可靠性回调
 *
 * @author Cade Li
 * @since 2023/8/18
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RabbitCallback implements ConfirmCallback, ReturnsCallback, InitializingBean {

    /**
     * 交换机名称映射
     */
    private Map<String, List<Exchange>> exchangeNameMap;

    private final List<Exchange> exchanges;

    private final RabbitTemplate rabbitTemplate;

    private final AbstractTxMsgHandler txMsgTaskHandler;

    /**
     * 消息是否成功到达交换机
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        // 获取 correlationData id
        String correlationId = ObjUtil.defaultIfNull(correlationData, CorrelationData::getId, "");
        if (ack) {
            log.debug("Rabbit message send ok, id:{}", correlationId);
            txMsgTaskHandler.handleConfirm(correlationData, true, cause);
            return;
        }
        log.error("Rabbit message send fail, correlationId:{}, cause:{}", correlationId, cause);
        txMsgTaskHandler.handleConfirm(correlationData, false, cause);
    }

    /**
     * 消息是否成功投递到队列
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        // 由于 delay 交换机会默认执行退回回调，不需要处理
        if (isExchangeDelayed(returned.getExchange())) {
            return;
        }
        log.error("Rabbit message is returned, message:{}, replyCode:{}. replyText:{}, exchange:{}, routingKey :{}",
                returned.getMessage(), returned.getReplyCode(), returned.getReplyText(), returned.getExchange(), returned.getRoutingKey());
        txMsgTaskHandler.handleReturned(returned);
    }

    /**
     * 判断是否是 delay 交换机
     */
    private boolean isExchangeDelayed(String exchangeName) {
        if (ObjUtil.isEmpty(exchangeNameMap)) {
            exchangeNameMap = exchanges.stream().collect(Collectors.groupingBy(Exchange::getName, Collectors.toList()));
        }
        // 若是 delay 交换机
        List<Exchange> exchangeList = exchangeNameMap.get(exchangeName);
        return exchangeList.stream()
                .allMatch(exchange -> ObjUtil.isNotNull(exchange) && (exchange.isDelayed() || RabbitConst.EXC_TYPE_DELAYED.equals(exchange.getType())));
    }

    @Override
    public void afterPropertiesSet() {
        // 开启 ReturnsCallback
        rabbitTemplate.setMandatory(true);
        // 设置回调
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
        // 设置 correlationData 后置处理
        rabbitTemplate.setCorrelationDataPostProcessor((message, correlationData) -> {
            // 填充 correlationDataId 到 MessageProperties
            if (ObjUtil.isNotNull(correlationData) && ObjUtil.isNotNull(correlationData.getId())) {
                String correlationId = correlationData.getId();
                message.getMessageProperties().setCorrelationId(correlationId);
            }
            return correlationData;
        });
    }
}
