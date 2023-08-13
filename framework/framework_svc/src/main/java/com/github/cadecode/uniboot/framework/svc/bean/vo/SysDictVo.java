package com.github.cadecode.uniboot.framework.svc.bean.vo;

import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 系统字典 VO
 *
 * @author Cade Li
 * @date 2023/6/11
 */
public class SysDictVo {

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class SysDictPageReqVo extends PageParams {
        private String name;
        private String type;
    }

    @Data
    public static class SysDictAddReqVo {
        @NotEmpty
        private String name;
        @NotEmpty
        private String type;
        private String label;
        @NotEmpty
        private String value;
        @NotNull
        private Boolean defaultFlag;
        private String description;
        @NotNull
        private Integer orderNum;
    }

    @Data
    public static class SysDictUpdateReqVo {
        @NotNull
        private Long id;
        @NotEmpty
        private String name;
        @NotEmpty
        private String type;
        private String label;
        @NotEmpty
        private String value;
        @NotNull
        private Boolean defaultFlag;
        private String description;
        @NotNull
        private Integer orderNum;
    }

    @Data
    public static class SysDictPageResVo {
        private Long id;
        private String name;
        private String type;
        private String label;
        private String value;
        private Boolean defaultFlag;
        private String description;
        private Integer orderNum;
        private Date createTime;
        private Date updateTime;
        private String updateUser;
    }

    @Data
    public static class SysDictGetByTypeResVo {
        private Long id;
        private String name;
        private String type;
        private String label;
        private String value;
        private String description;
        private Integer orderNum;
    }

    @Data
    public static class SysDictSuggestResVo {
        private String name;
        private String type;
    }

}
