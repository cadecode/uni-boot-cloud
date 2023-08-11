package com.github.cadecode.uniboot.framework.svc.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 系统接口 VO
 *
 * @author Cade Li
 * @date 2022/5/24
 */
public class SysApiVo {

    @Data
    public static class SysApiRolesVo {

        private Long id;

        private String url;

        private String description;

        private Date createTime;

        private Date updateTime;

        private String updateUser;

        private List<String> roles;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SysApiSwaggerVo {

        private String url;

        private String description;
    }
}
