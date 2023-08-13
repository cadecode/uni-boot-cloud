package com.github.cadecode.uniboot.framework.api.bean.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * SysApi res dto
 *
 * @author Cade Li
 * @since 2023/8/11
 */
public class SysApiDto {

    @Data
    public static class SysApiRolesResDto {

        private Long id;

        private String url;

        private String description;

        private Date createTime;

        private Date updateTime;

        private String updateUser;

        private List<String> roles;
    }
}
