package com.github.cadecode.uniboot.common.plugin.job.config;

import com.github.cadecode.uniboot.common.plugin.job.config.XxlJobProperties.XxlJobAdminProperties;
import com.github.cadecode.uniboot.common.plugin.job.config.XxlJobProperties.XxlJobExecutorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job 自动配置
 *
 * @author Cade Li
 * @since 2023/11/10
 */
@Deprecated
@Slf4j
@ConditionalOnProperty(name = "xxl.job.enabled", havingValue = "true")
@EnableConfigurationProperties({XxlJobProperties.class, XxlJobAdminProperties.class, XxlJobExecutorProperties.class})
@Configuration
public class XxlJobAutoConfig {

    /* @Bean
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperties xxlJobProperties,
                                               XxlJobAdminProperties adminProperties,
                                               XxlJobExecutorProperties executorProperties) {
        log.info("xxl-job config init");
        // init executor
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminProperties.getAddresses());
        xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        xxlJobSpringExecutor.setAppname(executorProperties.getAppname());
        xxlJobSpringExecutor.setAddress(executorProperties.getAddress());
        xxlJobSpringExecutor.setIp(executorProperties.getIp());
        xxlJobSpringExecutor.setPort(executorProperties.getPort());
        xxlJobSpringExecutor.setLogPath(executorProperties.getLogpath());
        xxlJobSpringExecutor.setLogRetentionDays(executorProperties.getLogRetentionDays());
        return xxlJobSpringExecutor;
    } */
}
