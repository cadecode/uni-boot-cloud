package com.github.cadecode.uniboot.framework.request;

import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 系统API请求对象
 *
 * @author Cade Li
 * @date 2023/5/14
 */
public class SysApiRequest {

    @Data
    public static class SysApiRolesRequest extends PageParams {
        private String url;
        private String description;
    }

    @Data
    public static class SysApiUpdateRequest {
        @NotNull
        private Long id;
        private String url;
        private String description;
    }

    @Data
    public static class SysApiAddRequest {
        @NotEmpty
        private String url;
        @NotEmpty
        private String description;
    }
}
