package com.github.cadecode.uniboot.framework.svc.bean.vo;

import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @EqualsAndHashCode(callSuper = true)
    public static class SysApiRolesReqVo extends PageParams {
        private String url;
        private String description;
    }

    @Data
    public static class SysApiUpdateReqVo {
        @NotNull
        private Long id;
        private String url;
        private String description;
    }

    @Data
    public static class SysApiAddReqVo {
        @NotNull
        @NotEmpty
        private String url;
        @NotNull
        @NotEmpty
        private String description;
    }

    @Data
    public static class SysApiRolesResVo {

        private Long id;

        private String url;

        private String description;

        private Date createTime;

        private Date updateTime;

        private String updateUser;

        private List<String> roles;
    }
}
