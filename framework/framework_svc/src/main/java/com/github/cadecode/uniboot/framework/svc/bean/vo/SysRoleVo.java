package com.github.cadecode.uniboot.framework.svc.bean.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 系统角色 VO
 *
 * @author Cade Li
 * @date 2022/5/24
 */
public class SysRoleVo {

    @Data
    public static class SysRoleListVo {

        private Long id;

        private String code;

        private String name;

        private String description;

        private Date createTime;

        private Date updateTime;

        private String updateUser;
    }

    @Data
    public static class SysRoleUnionVo {

        private Long id;

        private String code;

        private String name;

        private String description;

        private Date createTime;

        private Date updateTime;

        private String updateUser;

        private List<Long> menus;

        private List<Long> apis;
    }
}
