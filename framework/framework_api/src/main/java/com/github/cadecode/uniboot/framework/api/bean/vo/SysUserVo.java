package com.github.cadecode.uniboot.framework.api.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

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
    public static class SysUserRolesVo {

        private Long id;

        private String username;

        @JsonIgnore
        private String password;

        private String nickName;

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
}
