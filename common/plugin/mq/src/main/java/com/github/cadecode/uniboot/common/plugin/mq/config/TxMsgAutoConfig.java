package com.github.cadecode.uniboot.common.plugin.mq.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 事务消息自动配置
 *
 * @author Cade Li
 * @since 2023/8/20
 */
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(TxMsgProperties.class)
@Configuration
public class TxMsgAutoConfig {

}
