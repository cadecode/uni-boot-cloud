package com.github.cadecode.uniboot.framework.svc.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysMenuVo.SysMenuTreeVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SysUserInfoVo {
        private List<SysMenuTreeVo> menuList;
    }
}
