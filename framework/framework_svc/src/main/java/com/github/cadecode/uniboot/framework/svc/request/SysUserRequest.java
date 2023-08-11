package com.github.cadecode.uniboot.framework.svc.request;

import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统用户请求对象
 *
 * @author Cade Li
 * @since 2023/4/22
 */
public class SysUserRequest {

    @Data
    public static class SysUserModifyInfoRequest{
        @NotEmpty
        private String nickName;
        private String phone;
        @Email
        private String mail;
        private String sex;
    }

    @Data
    public static class SysUserModifyPassRequest{
        @NotEmpty
        private String oldPass;
        @NotEmpty
        private String newPass;
        @NotEmpty
        private String confirmedPass;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SysUserRolesRequest extends PageParams {
        private String username;
        private String nickName;
        private List<Long> roleIdList;
        private Boolean enableFlag;
    }

    @Data
    public static class SysUserUpdateEnableRequest{
        @NotNull
        private Long id;
        @NotNull
        private Boolean enableFlag;
    }

    @Data
    public static class SysUserUpdateRequest{
        @NotNull
        private Long id;
        @NotEmpty
        private String username;
        @NotEmpty
        private String nickName;
        private String password;
        private String phone;
        @Email
        private String mail;
        @NotEmpty
        private String sex;
    }

    @Data
    public static class SysUserAddRequest{
        @NotEmpty
        private String username;
        @NotEmpty
        private String nickName;
        @NotEmpty
        private String password;
        private String phone;
        @Email
        private String mail;
        @NotEmpty
        private String sex;
        @NotNull
        private Boolean enableFlag;
    }
}
