package com.github.cadecode.uniboot.framework.svc.bean.vo;

import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    public static class SysRoleMappingReqVo {
        @NotNull
        private Long id;
        @NotNull
        private Long roleId;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SysRoleUnionReqVo extends PageParams {
        private String code;
        private String name;
    }

    @Data
    public static class SysRoleUpdateReqVo {
        @NotNull
        private Long id;
        private String code;
        private String name;
        private String description;
    }

    @Data
    public static class SysRoleAddReqVo {
        @NotEmpty
        private String code;
        @NotEmpty
        private String name;
        @NotEmpty
        private String description;
    }

    @Data
    public static class SysRoleListResVo {

        private Long id;

        private String code;

        private String name;

        private String description;

        private Date createTime;

        private Date updateTime;

        private String updateUser;
    }

    @Data
    public static class SysRoleUnionResVo {

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
