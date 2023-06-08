package top.cadecode.uniboot.framework.request;

import lombok.Data;
import top.cadecode.uniboot.common.core.web.response.PageParams;
import top.cadecode.uniboot.common.plugin.log.enums.LogTypeEnum;

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
    public static class SysLogPageRequest extends PageParams {

        private Date startTime;
        private Date endTime;
        private List<LogTypeEnum> logTypeList;
        private String url;
        private String accessUser;
        private Boolean exceptional;

    }
}
