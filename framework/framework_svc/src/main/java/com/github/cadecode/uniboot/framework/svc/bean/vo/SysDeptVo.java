package com.github.cadecode.uniboot.framework.svc.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 部门 VO
 *
 * @author Cade Li
 * @since 2023/11/24
 */
public class SysDeptVo {

    @Data
    public static class SysDeptTreeResVo {
        private Long id;
        private String deptName;
        private Long parentId;
        private Integer orderNum;
        private String leader;
        private String mail;
        private String phone;
        private Date createTime;
        private Date updateTime;
        private String updateUser;
        private List<SysDeptTreeResVo> children;
    }

    @Data
    public static class SysDeptQueryResVo {
        private Long id;
        private String deptName;
        private Long parentId;
        private Integer orderNum;
        private String leader;
        private String mail;
        private String phone;
        private Date createTime;
        private Date updateTime;
        private String updateUser;
    }

    @Data
    public static class SysDeptUpdateReqVo {
        @NotNull
        private Long id;
        private String deptName;
        private Integer orderNum;
        private String leader;
        private String mail;
        private String phone;
    }

    @Data
    public static class SysDeptAddReqVo {
        private Long parentId;
        @NotEmpty
        private String deptName;
        @NotNull
        private Integer orderNum;
        private String leader;
        private String mail;
        private String phone;
    }
}
