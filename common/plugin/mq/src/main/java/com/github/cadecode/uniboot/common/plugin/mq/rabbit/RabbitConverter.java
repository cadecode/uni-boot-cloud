package com.github.cadecode.uniboot.common.plugin.mq.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.amqp.support.converter.SmartMessageConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * 通用 RabbitMq 消息转换器
 * 支持常见两种形式 text 与 json
 *
 * @author Cade Li
 * @since 2023/8/18
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RabbitConverter implements SmartMessageConverter {

    private final SimpleMessageConverter simpleMessageConverter = new SimpleMessageConverter();
    private final Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        return toMessage(object, messageProperties, null);
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties, Type genericType) throws MessageConversionException {
        if (object instanceof byte[] || object instanceof String) {
            return simpleMessageConverter.toMessage(object, messageProperties, genericType);
        }
        return jackson2JsonMessageConverter.toMessage(object, messageProperties, genericType);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return fromMessage(message, null);
    }

    @Override
    public Object fromMessage(Message message, Object conversionHint) throws MessageConversionException {
        MessageProperties properties = message.getMessageProperties();
        if (properties == null) {
            return message.getBody();
        }
        String contentType = properties.getContentType();
        if (contentType != null && contentType.startsWith("text")) {
            return simpleMessageConverter.fromMessage(message);
        }
        if (contentType != null && contentType.equals(MessageProperties.CONTENT_TYPE_JSON)) {
            return jackson2JsonMessageConverter.fromMessage(message, conversionHint);
        }
        return message.getBody();
    }
}
