package com.github.cadecode.uniboot.gateway;

import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.plugin.core.config.EnablePluginRegistries;

/**
 * 启动类
 */
@EnablePluginRegistries({StrategyService.class})
@EnableFeignClients("com.github.cadecode")
@EnableDiscoveryClient
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class UniGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniGatewayApplication.class, args);
    }
}
