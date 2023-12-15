package com.github.cadecode.uniboot.framework.svc.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysMenuVo.SysMenuTreeResVo;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 系统用户 VO
 *
 * @author Cade Li
 * @date 2022/5/24
 */
public class SysUserVo {

    @Data
    public static class SysUserModifyInfoReqVo {
        @NotNull
        @NotEmpty
        private String nickName;
        private String phone;
        @Email
        private String mail;
        private String sex;
    }

    @Data
    public static class SysUserModifyPassReqVo {
        @NotNull
        @NotEmpty
        private String oldPass;
        @NotNull
        @NotEmpty
        private String newPass;
        @NotNull
        @NotEmpty
        private String confirmedPass;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SysUserRolesReqVo extends PageParams {
        private String username;
        private String nickName;
        private Long deptId;
        private List<Long> roleIdList;
        private Boolean enableFlag;
    }

    @Data
    public static class SysUserUpdateEnableReqVo {
        @NotNull
        private Long id;
        @NotNull
        private Boolean enableFlag;
    }

    @Data
    public static class SysUserUpdateReqVo {
        @NotNull
        private Long id;
        @NotNull
        @NotEmpty
        private String username;
        @NotNull
        @NotEmpty
        private String nickName;
        private Long deptId;
        private String password;
        private String phone;
        @Email
        private String mail;
        @NotNull
        @NotEmpty
        private String sex;
    }

    @Data
    public static class SysUserAddReqVo {
        @NotNull
        @NotEmpty
        private String username;
        @NotNull
        @NotEmpty
        private String nickName;
        @NotNull
        private Long deptId;
        @NotNull
        @NotEmpty
        private String password;
        private String phone;
        @Email
        private String mail;
        @NotNull
        @NotEmpty
        private String sex;
        @NotNull
        private Boolean enableFlag;
    }

    @Data
    public static class SysUserRolesResVo {

        private Long id;
        private String username;
        @JsonIgnore
        private String password;
        private String nickName;
        private Long deptId;
        private String deptName;
        private Boolean enableFlag;
        private String sex;
        private String mail;
        private String phone;
        private String loginIp;
        private Date loginDate;
        private Date createTime;
        private Date updateTime;
        private String updateUser;
        private List<String> roles;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SysUserInfoResVo {
        private List<SysMenuTreeResVo> menuList;
    }
}
