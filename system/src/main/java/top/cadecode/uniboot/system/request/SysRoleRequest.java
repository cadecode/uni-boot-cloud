package top.cadecode.uniboot.system.request;

import lombok.Data;

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
}
