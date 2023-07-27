package com.github.cadecode.uniboot.framework.api.request;

import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 角色请求对象
 *
 * @author Cade Li
 * @since 2023/5/4
 */
public class SysRoleRequest {

    @Data
    public static class SysRoleMappingRequest {
        @NotNull
        private Long id;
        @NotNull
        private Long roleId;
    }

    @Data
    public static class SysRoleUnionRequest extends PageParams {
        private String code;
        private String name;
    }

    @Data
    public static class SysRoleUpdateRequest {
        @NotNull
        private Long id;
        private String code;
        private String name;
        private String description;
    }

    @Data
    public static class SysRoleAddRequest {
        @NotEmpty
        private String code;
        @NotEmpty
        private String name;
        @NotEmpty
        private String description;
    }
}
