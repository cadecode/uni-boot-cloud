package com.github.cadecode.uniboot.framework.svc.bean.vo;

import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 系统日志 VO
 *
 * @author Cade Li
 * @since 2023/5/26
 */
public class SysLogVo {


    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SysLogPageReqVo extends PageParams {

        private Date startTime;
        private Date endTime;
        private List<String> logTypeList;
        private String url;
        private String accessUser;
        private Boolean exceptional;

    }

    @Data
    public static class SysLogSaveReqVo {

        private String logType;

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

    @Data
    public static class SysLogPageResVo {

        private Long id;

        private String logType;

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

        private Date createTime;

        private Date updateTime;

        private String updateUser;

    }
}
