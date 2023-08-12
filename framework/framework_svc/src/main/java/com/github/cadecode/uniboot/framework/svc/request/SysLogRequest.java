package com.github.cadecode.uniboot.framework.svc.request;

import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import com.github.cadecode.uniboot.common.plugin.log.enums.LogTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 系统日志请求对象
 *
 * @author Cade Li
 * @since 2023/5/26
 */
public class SysLogRequest {

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SysLogPageRequest extends PageParams {

        private Date startTime;
        private Date endTime;
        private List<LogTypeEnum> logTypeList;
        private String url;
        private String accessUser;
        private Boolean exceptional;

    }

    @Data
    public static class SysLogSaveRequest {

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

        private String traceId;

    }
}
