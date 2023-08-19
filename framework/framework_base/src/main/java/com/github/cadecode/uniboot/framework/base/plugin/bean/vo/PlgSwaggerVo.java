package com.github.cadecode.uniboot.framework.base.plugin.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * plugin swagger vo
 *
 * @author Cade Li
 * @since 2023/8/19
 */
public class PlgSwaggerVo {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PlgSwaggerDescResVo {

        private String url;

        private String description;
    }
}
