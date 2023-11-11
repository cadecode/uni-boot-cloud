package com.github.cadecode.uniboot.common.plugin.mq.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TxMsg 配置
 *
 * @author Cade Li
 * @since 2023/8/19
 */
@Data
@ConfigurationProperties(TxMsgProperties.ENV_CONF_TX_MSG_PREFIX)
public class TxMsgProperties implements InitializingBean {

    public static final String ENV_CONF_TX_MSG_PREFIX = "uni-boot.mq.tx-msg";

    public static final long DEFAULT_RETRY_FIX_DELAY = 30 * 1000;

    public static final long DEFAULT_CLEAR_FIX_DELAY = 2 * 60 * 60 * 1000;

    public static final long DEFAULT_AUTO_CLEAR_INTERVAL = 60 * 60 * 1000;

    // 重试默认属性

    public static final int DEFAULT_MAX_RETRY_TIMES = 3;

    public static final long DEFAULT_BACKOFF_INIT_INTERVAL = 10 * 1000;

    public static final double DEFAULT_BACKOFF_MULTIPLIER = 2.0;

    public static final long DEFAULT_BACKOFF_MAX_INTERVAL = 30 * 60 * 1000;

    // 重试默认属性 --end

    /**
     * 是否开启重试
     */
    private Boolean enableRetry = false;

    /**
     * 定时重试间隔时间
     */
    private Long retryFixDelay = DEFAULT_RETRY_FIX_DELAY;

    /**
     * 定时清理记录间隔时间
     */
    private Long clearFixDelay = DEFAULT_CLEAR_FIX_DELAY;

    /**
     * 是否开启自动清理
     */
    private Boolean autoClear = false;

    /**
     * 自动清理多久之前的记录
     */
    private Long autoClearInterval = DEFAULT_AUTO_CLEAR_INTERVAL;

    /**
     * 事务消息配置
     */
    private MsgOption defaultMsgOption;

    public MsgOption createMsgOption(MsgOption msgOption) {
        if (ObjUtil.isNull(msgOption)) {
            msgOption = new MsgOption();
        }
        BeanUtil.copyProperties(defaultMsgOption, msgOption, CopyOptions.create().setOverride(false));
        return msgOption;
    }

    @Override
    public void afterPropertiesSet() {
        MsgOption allDefaultOption = MsgOption.builder()
                .maxRetryTimes(DEFAULT_MAX_RETRY_TIMES)
                .backoffInitInterval(DEFAULT_BACKOFF_INIT_INTERVAL)
                .backoffMultiplier(DEFAULT_BACKOFF_MULTIPLIER)
                .backoffMaxInterval(DEFAULT_BACKOFF_MAX_INTERVAL)
                .build();
        if (ObjUtil.isNull(defaultMsgOption)) {
            defaultMsgOption = allDefaultOption;
        } else {
            BeanUtil.copyProperties(allDefaultOption, defaultMsgOption, CopyOptions.create().setOverride(false));
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MsgOption {

        // 重试相关属性

        private Integer maxRetryTimes;

        private Long backoffInitInterval;

        private Double backoffMultiplier;

        private Long backoffMaxInterval;

        // 重试相关属性 -- end
    }

}
