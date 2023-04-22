package top.cadecode.uniboot.system.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
}
