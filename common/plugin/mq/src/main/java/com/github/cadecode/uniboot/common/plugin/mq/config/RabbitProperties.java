package com.github.cadecode.uniboot.common.plugin.mq.config;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjUtil;
import lombok.Data;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * RabbitMQ 配置项
 * for spring-boot-starter-amqp 2.5.4
 *
 * @author Cade Li
 * @since 2023/8/17
 */
@Data
@ConfigurationProperties(RabbitProperties.ENV_CONF_RABBIT_PREFIX)
public class RabbitProperties {

    public static final String ENV_CONF_RABBIT_PREFIX = "uni-boot.mq.rabbit";

    private boolean enable;

    private List<ExchangeBuildProperties> exchanges;

    private List<QueueBuildProperties> queues;

    private List<BindingBuildProperties> bindings;

    /**
     * 队列声明常用配置项
     * 默认持久化、不独占、不自动删除
     */
    @Data
    public static class QueueBuildProperties {
        /**
         * 队列名称
         */
        private String name;
        /**
         * 名称是否添加随机后缀
         * 注：随机后缀名适用于发布订阅模式，每个消费者监听各自队列
         * 当需要在 @RabbitListener 注解中指定 queues，可以使用 SpEL 表达式获取完整队列名称
         */
        private boolean randomSuffix;
        private boolean durable = true;
        private boolean exclusive;
        private boolean autoDelete;
        private String dlExchange;
        private String dlRoutingKey;
        private Map<String, Object> arguments;

        public QueueBuilder toBuilder() {
            String completedName = name;
            if (randomSuffix) {
                completedName = completedName + "." + UUID.fastUUID().toString(true);
            }
            QueueBuilder builder;
            if (durable) {
                builder = QueueBuilder.durable(completedName);
            } else {
                builder = QueueBuilder.nonDurable(completedName);
            }
            if (exclusive) {
                builder.exclusive();
            }
            if (autoDelete) {
                builder.autoDelete();
            }
            if (!ObjUtil.hasNull(dlExchange, dlRoutingKey)) {
                builder.deadLetterExchange(dlExchange);
                builder.deadLetterRoutingKey(dlRoutingKey);
            }
            if (ObjUtil.isNotEmpty(arguments)) {
                arguments.forEach(builder::withArgument);
            }
            return builder;
        }
    }

    /**
     * 交换机声明配置项
     * 默认持久化、不自动删除
     * 默认类型为 topic
     */
    @Data
    public static class ExchangeBuildProperties {
        private String name;
        private String type = ExchangeTypes.TOPIC;
        private boolean durable = true;
        private boolean autoDelete;
        private boolean internal;
        private boolean delayed;
        private boolean ignoreDeclarationExceptions;
        private boolean declare = true;
        private Object[] declaringAdmins;
        private Map<String, Object> arguments;

        public ExchangeBuilder toBuilder() {
            ExchangeBuilder builder = new ExchangeBuilder(name, type);
            builder.durable(durable);
            if (autoDelete) {
                builder.autoDelete();
            }
            if (internal) {
                builder.internal();
            }
            if (delayed) {
                builder.delayed();
            }
            if (ignoreDeclarationExceptions) {
                builder.ignoreDeclarationExceptions();
            }
            if (!declare) {
                builder.suppressDeclaration();
            }
            if (ObjUtil.isNotEmpty(declaringAdmins)) {
                builder.admins(arguments);
            }
            if (ObjUtil.isNotEmpty(arguments)) {
                arguments.forEach(builder::withArgument);
            }
            return builder;
        }
    }

    /**
     * 绑定关系声明配置项
     * 可绑定队列到交换机、交换机到交互机
     * 默认绑定类型为队列到交换机
     */
    @Data
    public static class BindingBuildProperties {
        private String bindName;
        private DestinationType bindType = DestinationType.QUEUE;
        private String exchangeName;
        private String routingKey;
        private Map<String, Object> arguments;
    }

}
