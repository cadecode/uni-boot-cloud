package com.github.cadecode.uniboot.framework.base.plugin.bean.vo;

import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import com.github.cadecode.uniboot.framework.base.plugin.enums.ConsumeStateEnum;
import com.github.cadecode.uniboot.framework.base.plugin.enums.SendStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * MQ 消息实体 VO
 *
 * @author Cade Li
 * @since 2023/8/19
 */
public class PlgMqMsgVo {

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class PlgMqMsgPageReqVo extends PageParams {
        private Date startCreateTime;
        private Date endCreateTime;
        private String bizType;
        private List<SendStateEnum> sendStateList;
    }

    @Data
    public static class PlgMqMsgPageResVo {
        private String id;
        private String bizType;
        private String bizKey;
        private String exchange;
        private String routingKey;
        private String message;
        private SendStateEnum sendState;
        private ConsumeStateEnum consumeState;
        private Date nextRetryTime;
        private String cause;
        private Integer leftRetryTimes;
        private Integer maxRetryTimes;
        private Long backoffInitInterval;
        private Double backoffMultiplier;
        private Long backoffMaxInterval;
        private Date createTime;
        private Date updateTime;
        private String updateUser;
    }

    @Data
    public static class PlgMqMsgUpdateReqVo {
        @NotNull
        private String id;
        private SendStateEnum sendState;
        private Integer leftRetryTimes;
    }
}
