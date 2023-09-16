package com.github.cadecode.uniboot.framework.base.plugin.bean.po;

import com.baomidou.mybatisplus.annotation.*;
import com.github.cadecode.uniboot.framework.base.plugin.enums.ConsumeStateEnum;
import com.github.cadecode.uniboot.framework.base.plugin.enums.SendStateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * MQ 消息实体
 *
 * @author Cade Li
 * @since 2023/8/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(autoResultMap = true)
public class PlgMqMsg {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String bizType;

    private String bizKey;

    private String exchange;

    private String routingKey;

    private String message;

    private String connectionName;

    private SendStateEnum sendState;

    private ConsumeStateEnum consumeState;

    private Date nextRetryTime;

    private String cause;

    private Integer leftRetryTimes;

    private Integer maxRetryTimes;

    private Long backoffInitInterval;

    private Double backoffMultiplier;

    private Long backoffMaxInterval;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
}
