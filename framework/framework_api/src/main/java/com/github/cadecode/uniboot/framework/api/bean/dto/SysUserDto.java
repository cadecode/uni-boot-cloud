package com.github.cadecode.uniboot.framework.api.bean.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * SysUser Dto
 *
 * @author Cade Li
 * @since 2023/10/31
 */
public class SysUserDto {

    @Data
    public static class SysUserGetByUsernameResDto {
        private Long id;

        private String username;

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
