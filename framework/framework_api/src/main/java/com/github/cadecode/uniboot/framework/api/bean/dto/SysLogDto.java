package com.github.cadecode.uniboot.framework.api.bean.dto;

import com.github.cadecode.uniboot.common.plugin.log.enums.LogTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统日志 DTO
 *
 * @author Cade Li
 * @since 2023/5/26
 */
public class SysLogDto {

    /**
     * 请求日志信息类
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SysLogSaveDto {

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
    }
}
