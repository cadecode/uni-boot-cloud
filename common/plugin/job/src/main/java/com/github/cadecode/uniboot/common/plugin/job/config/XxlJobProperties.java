package com.github.cadecode.uniboot.common.plugin.job.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * xxl-job 配置项
 *
 * @author Cade Li
 * @since 2023/11/10
 */
@ConfigurationProperties(prefix = "xxl.job")
@Data
public class XxlJobProperties {

    private Boolean enabled;

    private String accessToken;


    @ConfigurationProperties(prefix = "xxl.job.admin")
    @Data
    public static class XxlJobAdminProperties {

        private String addresses;

    }

    @ConfigurationProperties(prefix = "xxl.job.executor")
    @Data
    public static class XxlJobExecutorProperties {

        private String appname;

        private String address;

        private String ip;

        private int port;

        private String logpath;

        private int logRetentionDays;
    }

}
