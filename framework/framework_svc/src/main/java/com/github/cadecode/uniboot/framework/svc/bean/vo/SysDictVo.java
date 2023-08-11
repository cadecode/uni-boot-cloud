package com.github.cadecode.uniboot.framework.svc.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * 系统字典 VO
 *
 * @author Cade Li
 * @date 2023/6/11
 */
public class SysDictVo {

    @Data
    public static class SysDictPageVo {
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
    public static class SysDictGetByTypeVo {
        private Long id;
        private String name;
        private String type;
        private String label;
        private String value;
        private String description;
        private Integer orderNum;
    }

    @Data
    public static class SysDictSuggestVo {
        private String name;
        private String type;
    }

}
