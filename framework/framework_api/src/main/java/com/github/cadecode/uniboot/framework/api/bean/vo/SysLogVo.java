package com.github.cadecode.uniboot.framework.api.bean.vo;

import com.github.cadecode.uniboot.common.plugin.log.enums.LogTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * 系统日志 VO
 *
 * @author Cade Li
 * @since 2023/5/26
 */
public class SysLogVo {

    @Data
    public static class SysLogPageVo {

        private Long id;

        private LogTypeEnum logType;

        private String url;

        private Boolean exceptional;

        private String accessUser;

        private String description;

        private String classMethod;

        private String threadId;

        private String threadName;

        private String ip;

        private String httpMethod;

        private String requestParams;

        private String result;

        private Long timeCost;

        private String os;

        private String browser;

        private String userAgent;

        private Date createTime;

        private Date updateTime;

        private String updateUser;
    }
}
