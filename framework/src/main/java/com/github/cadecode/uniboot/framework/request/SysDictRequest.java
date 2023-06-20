package com.github.cadecode.uniboot.framework.request;

import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 系统字典 请求
 *
 * @author Cade Li
 * @date 2023/6/11
 */
public class SysDictRequest {

    @Data
    public static class SysDictPageRequest extends PageParams {
        private String name;
        private String type;
    }

    @Data
    public static class SysDictAddRequest {
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
    public static class SysDictUpdateRequest {
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
}
