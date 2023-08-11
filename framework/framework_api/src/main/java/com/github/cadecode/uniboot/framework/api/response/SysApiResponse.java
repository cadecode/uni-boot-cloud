package com.github.cadecode.uniboot.framework.api.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * SysApi response
 *
 * @author Cade Li
 * @since 2023/8/11
 */
public class SysApiResponse {

    @Data
    public static class SysApiRolesResponse {

        private Long id;

        private String url;

        private String description;

        private Date createTime;

        private Date updateTime;

        private String updateUser;

        private List<String> roles;
    }
}
