package com.github.cadecode.uniboot.framework.api.config;

import com.github.cadecode.uniboot.framework.api.feign.FeignClientDecorator;
import feign.Client;
import feign.Client.Default;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.cloud.openfeign.loadbalancer.RetryableFeignBlockingLoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenFeign 配置类
 *
 * @author Cade Li
 * @since 2023/7/29
 */
@Configuration
public class FeignConfig {

    // 覆盖 feign 配置
    // org.springframework.cloud.openfeign.loadbalancer.DefaultFeignLoadBalancerConfiguration

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.loadbalancer.retry.enabled", havingValue = "false")
    public Client feignClient(LoadBalancerClient loadBalancerClient, LoadBalancerProperties properties,
                              LoadBalancerClientFactory loadBalancerClientFactory) {
        FeignClientDecorator decorator = new FeignClientDecorator(new Default(null, null));
        return new FeignBlockingLoadBalancerClient(decorator, loadBalancerClient, properties, loadBalancerClientFactory);
    }

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.loadbalancer.retry.enabled", havingValue = "true", matchIfMissing = true)
    public Client feignRetryClient(LoadBalancerClient loadBalancerClient, LoadBalancedRetryFactory loadBalancedRetryFactory,
                                   LoadBalancerProperties properties, LoadBalancerClientFactory loadBalancerClientFactory) {
        FeignClientDecorator decorator = new FeignClientDecorator(new Default(null, null));
        return new RetryableFeignBlockingLoadBalancerClient(decorator, loadBalancerClient, loadBalancedRetryFactory, properties, loadBalancerClientFactory);
    }

}
