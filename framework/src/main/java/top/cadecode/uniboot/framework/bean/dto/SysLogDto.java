package top.cadecode.uniboot.framework.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.cadecode.uniboot.common.core.enums.LogTypeEnum;

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
    public static class SysLogInfoDto {

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
