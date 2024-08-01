package com.github.cadecode.uniboot.common.plugin.mq.config;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.amqp.AbstractRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.MultiRabbitConstants;
import org.springframework.boot.autoconfigure.amqp.MultiRabbitProperties;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * RabbitMQ 自动配置
 *
 * @author Cade Li
 * @since 2023/8/17
 */
@Slf4j
@EnableConfigurationProperties(RabbitProperties.class)
@Configuration
@ConditionalOnProperty(name = "uni-boot.mq.rabbit.enable", havingValue = "true")
public class RabbitAutoConfig {

    @Bean
    public MultiRabbitContainerFactoryConfig multiRabbitContainerFactoryConfig(MessageConverter messageConverter,
                                                                               MultiRabbitProperties multiRabbitProperties,
                                                                               SimpleRoutingConnectionFactory multiRabbitConnectionFactory,
                                                                               Map<String, SimpleRabbitListenerContainerFactory> rabbitListenerContainerFactoryMap) {
        return new MultiRabbitContainerFactoryConfig(messageConverter, multiRabbitProperties, multiRabbitConnectionFactory, rabbitListenerContainerFactoryMap);
    }

    @Bean
    public MultiRabbitDeclareBeanFactoryPostProcessor multiRabbitDeclareBeanFactoryPostProcessor() {
        return new MultiRabbitDeclareBeanFactoryPostProcessor();
    }

    /**
     * RabbitMQ ContainerFactory 配置
     * 提供对 MultiRabbit 支持
     */
    @RequiredArgsConstructor
    public static class MultiRabbitContainerFactoryConfig implements InitializingBean {

        private final MessageConverter messageConverter;
        private final MultiRabbitProperties multiRabbitProperties;
        private final SimpleRoutingConnectionFactory multiRabbitConnectionFactory;
        private final Map<String, SimpleRabbitListenerContainerFactory> rabbitListenerContainerFactoryMap;

        @Override
        public void afterPropertiesSet() {
            configureContainerFactory();
        }

        /**
         * 配置 SimpleRabbitListenerContainerFactory
         * spring-multirabbit {@link  org.springframework.boot.autoconfigure.amqp.MultiRabbitAutoConfiguration}
         * 自动配置 SimpleRabbitListenerContainerFactory, 部分配置有缺失，此方法用于补偿
         * 参考 {@link SimpleRabbitListenerContainerFactoryConfigurer}
         */
        private void configureContainerFactory() {
            multiRabbitProperties.getConnections().forEach((connectionName, props) -> {
                MultiRabbitContainerFactoryConfigurer configurer = new MultiRabbitContainerFactoryConfigurer(props.getListener().getSimple());
                configurer.setMessageConverter(messageConverter);
                SimpleRabbitListenerContainerFactory containerFactory = rabbitListenerContainerFactoryMap.get(connectionName);
                ConnectionFactory targetConnectionFactory = multiRabbitConnectionFactory.getTargetConnectionFactory(connectionName);
                configurer.configure(containerFactory, targetConnectionFactory);
            });
        }

        /**
         * MultiRabbit ContainerFactory 配置器
         */
        @RequiredArgsConstructor
        private static class MultiRabbitContainerFactoryConfigurer
                extends AbstractRabbitListenerContainerFactoryConfigurer<SimpleRabbitListenerContainerFactory> {

            private final org.springframework.boot.autoconfigure.amqp.RabbitProperties.SimpleContainer config;

            @Override
            public void configure(SimpleRabbitListenerContainerFactory factory, ConnectionFactory connectionFactory) {
                PropertyMapper map = PropertyMapper.get();
                configure(factory, connectionFactory, config);
                map.from(config::getConcurrency).whenNonNull().to(factory::setConcurrentConsumers);
                map.from(config::getMaxConcurrency).whenNonNull().to(factory::setMaxConcurrentConsumers);
                map.from(config::getBatchSize).whenNonNull().to(factory::setBatchSize);
                map.from(config::isConsumerBatchEnabled).to(factory::setConsumerBatchEnabled);
            }

            @Override
            public void setMessageConverter(MessageConverter messageConverter) {
                super.setMessageConverter(messageConverter);
            }

            @Override
            public void setMessageRecoverer(MessageRecoverer messageRecoverer) {
                super.setMessageRecoverer(messageRecoverer);
            }
        }
    }

    /**
     * RabbitMQ 交换机、队列等配置对象注入容器
     * 注入的 bean 名称格式为 connectionName_declareName
     * 使用 {@link org.springframework.amqp.core.Declarable} setAdminsThatShouldDeclare 方法指定 AmqpAdmin bean
     */
    public static class MultiRabbitDeclareBeanFactoryPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {
        private Environment environment;

        @Override
        public void setEnvironment(Environment environment) {
            this.environment = environment;
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
            BindResult<RabbitProperties> bindResult = Binder.get(environment).bind(RabbitProperties.ENV_CONF_RABBIT_PREFIX, RabbitProperties.class);
            RabbitProperties rabbitProperties = bindResult.orElse(null);
            if (ObjUtil.isNull(rabbitProperties)) {
                log.info("Rabbit env config {} not found", RabbitProperties.ENV_CONF_RABBIT_PREFIX);
                return;
            }
            rabbitProperties.getDeclares().forEach((connectionName, declare) -> {
                if (ObjUtil.isNotEmpty(declare.getExchanges())) {
                    declare.getExchanges().forEach(o -> {
                        Exchange exchange = o.toBuilder().build();
                        exchange.setAdminsThatShouldDeclare(getAdminBeanName(connectionName));
                        beanFactory.registerSingleton(geneDeclareBeanName(connectionName, o.getName()), exchange);
                        log.info("Rabbit auto register exchange, connection:{}, queue:{}, {}", connectionName, o.getName(), o);
                    });
                }
                if (ObjUtil.isNotEmpty(declare.getQueues())) {
                    declare.getQueues().forEach(o -> {
                        Queue queue = o.toBuilder().build();
                        queue.setAdminsThatShouldDeclare(getAdminBeanName(connectionName));
                        beanFactory.registerSingleton(geneDeclareBeanName(connectionName, o.getName()), queue);
                        log.info("Rabbit auto register queue, connection:{}, exchange:{}, {}", connectionName, o.getName(), o);
                    });
                }
                if (ObjUtil.isNotEmpty(declare.getBindings())) {
                    declare.getBindings().forEach(o -> {
                        String bindName = o.getBindName();
                        // 对随机后缀的队列，不能直接用 bean name，需要获取 queue 的 bean 再获取队列名称
                        if (o.getBindType() == DestinationType.QUEUE) {
                            Queue queue = beanFactory.getBean(geneDeclareBeanName(connectionName, o.getBindName()), Queue.class);
                            bindName = queue.getName();
                        }
                        Binding binding = new Binding(bindName, o.getBindType(), o.getExchangeName(), o.getRoutingKey(), o.getArguments());
                        binding.setAdminsThatShouldDeclare(getAdminBeanName(connectionName));
                        String declareName = o.getBindName() + "-" + o.getBindType() + "-" + o.getExchangeName() + "-" + o.getRoutingKey();
                        String beanName = geneDeclareBeanName(connectionName, declareName);
                        beanFactory.registerSingleton(beanName, binding);
                        log.info("Rabbit auto register binding, connection:{}, bind {} to {}, bindType:{}, {}", connectionName, o.getBindName(),
                                o.getExchangeName(), o.getBindType(), o);
                    });
                }
            });
        }

        public String geneDeclareBeanName(String connectionName, String declareName) {
            return connectionName + "_" + declareName;
        }

        public String getAdminBeanName(String connectionName) {
            return connectionName + MultiRabbitConstants.RABBIT_ADMIN_SUFFIX;
        }
    }
}
