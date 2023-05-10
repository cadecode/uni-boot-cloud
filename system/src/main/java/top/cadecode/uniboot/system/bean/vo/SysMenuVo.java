package top.cadecode.uniboot.system.bean.vo;

import lombok.Data;

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
    public static class SysMenuTreeVo {
        private Long id;

        private Long parentId;

        private String routeName;

        private String routePath;

        private String componentPath;

        private String menuName;

        private Boolean leafFlag;

        private String icon;

        private List<SysMenuTreeVo> children = new ArrayList<>();
    }

    @Data
    public static class SysMenuRolesVo {
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

        private List<String> roles;

        private Date createTime;

        private Date updateTime;

        private String updateUser;

    }
}
