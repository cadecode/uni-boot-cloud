package com.github.cadecode.uniboot.common.plugin.mq.config;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * RabbitMQ 自动配置
 *
 * @author Cade Li
 * @since 2023/8/17
 */
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(RabbitProperties.class)
@ConditionalOnProperty(name = "uni-boot.mq.rabbit.enable", havingValue = "true")
@Configuration
public class RabbitAutoConfig implements BeanFactoryPostProcessor, EnvironmentAware {

    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BindResult<RabbitProperties> bindResult = Binder.get(environment).bind(RabbitProperties.ENV_CONF_RABBIT_PREFIX, RabbitProperties.class);
        RabbitProperties rabbitProperties = bindResult.orElse(null);
        if (ObjectUtil.isNull(rabbitProperties)) {
            log.info("Rabbit env config {} not found", RabbitProperties.ENV_CONF_RABBIT_PREFIX);
            return;
        }
        if (ObjectUtil.isNotEmpty(rabbitProperties.getExchanges())) {
            rabbitProperties.getExchanges().forEach(o -> {
                Exchange exchange = o.toBuilder().build();
                beanFactory.registerSingleton(o.getName(), exchange);
                log.info("Rabbit auto register exchange, {}", o.getName());
            });
        }
        if (ObjectUtil.isNotEmpty(rabbitProperties.getQueues())) {
            rabbitProperties.getQueues().forEach(o -> {
                Queue queue = o.toBuilder().build();
                beanFactory.registerSingleton(o.getName(), queue);
                log.info("Rabbit auto register queue, {}", o.getName());
            });
        }
        if (ObjectUtil.isNotEmpty(rabbitProperties.getBindings())) {
            rabbitProperties.getBindings().forEach(o -> {
                String bindName = o.getBindName();
                // 对随机后缀的队列，不能直接用 bean name，需要获取 queue 的 bean 再获取队列名称
                if (o.getBindType() == DestinationType.QUEUE) {
                    Queue queue = beanFactory.getBean(o.getBindName(), Queue.class);
                    bindName = queue.getName();
                }
                Binding binding = new Binding(bindName, o.getBindType(), o.getExchangeName(), o.getRoutingKey(), o.getArguments());
                beanFactory.registerSingleton(o.getBindName() + "-" + o.getExchangeName(), binding);
                log.info("Rabbit auto register binding, {} to {}, bindType:{}", o.getBindName(), o.getExchangeName(), o.getBindType());
            });
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
