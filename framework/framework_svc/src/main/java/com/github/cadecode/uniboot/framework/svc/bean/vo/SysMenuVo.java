package com.github.cadecode.uniboot.framework.svc.bean.vo;

import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统菜单VO
 *
 * @author Cade Li
 * @since 2023/4/15
 */
public class SysMenuVo {

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SysMenuRolesReqVo extends PageParams {
        private String routeName;
        private String menuName;
        private List<Long> roleIdList;
        private Boolean enableFlag;
        private Long parentId;
        private Boolean hiddenFlag;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SysMenuPageReqVo extends PageParams {
        private String routeName;
        private String menuName;
        private Boolean enableFlag;
        private Long parentId;
        private Boolean hiddenFlag;
        private Boolean leafFlag;
    }

    @Data
    public static class SysMenuUpdateEnableReqVo {
        @NotNull
        private Long id;
        @NotNull
        private Boolean enableFlag;
    }

    @Data
    public static class SysMenuUpdateReqVo {
        @NotNull
        private Long id;
        @NotEmpty
        private String routeName;
        @NotEmpty
        private String routePath;
        private String componentPath;
        @NotEmpty
        private String menuName;
        private String icon;
        private Integer orderNum;
        private Boolean cacheFlag;
    }

    @Data
    public static class SysMenuAddReqVo {
        private Long parentId;
        @NotEmpty
        private String routeName;
        @NotEmpty
        private String routePath;
        private String componentPath;
        @NotEmpty
        private String menuName;
        @NotNull
        private Boolean leafFlag;
        private String icon;
        private Integer orderNum;
        @NotNull
        private Boolean enableFlag;
        @NotNull
        private Boolean hiddenFlag;
        @NotNull
        private Boolean cacheFlag;
    }

    @Data
    public static class SysMenuTreeResVo {
        private Long id;

        private Long parentId;

        private String routeName;

        private String routePath;

        private String componentPath;

        private String menuName;

        private Boolean leafFlag;

        private Boolean hiddenFlag;

        private Boolean cacheFlag;

        private String icon;

        private List<SysMenuTreeResVo> children = new ArrayList<>();
    }

    @Data
    public static class SysMenuRolesResVo {
        private Long id;

        private Long parentId;

        private String routeName;

        private String routePath;

        private String componentPath;

        private String menuName;

        private Boolean leafFlag;

        private String icon;

        private Integer orderNum;

        private Boolean enableFlag;

        private Boolean hiddenFlag;

        private Boolean cacheFlag;

        private List<String> roles;

        private Date createTime;

        private Date updateTime;

        private String updateUser;
    }

    @Data
    public static class SysMenuPageResVo {
        private Long id;

        private Long parentId;

        private String routeName;

        private String routePath;

        private String componentPath;

        private String menuName;

        private Boolean leafFlag;

        private String icon;

        private Integer orderNum;

        private Boolean enableFlag;

        private Boolean hiddenFlag;

        private Boolean cacheFlag;

        private Date createTime;

        private Date updateTime;

        private String updateUser;
    }
}
