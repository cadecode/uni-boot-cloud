package com.github.cadecode.uniboot.common.plugin.log.model;

import com.github.cadecode.uniboot.common.plugin.log.annotation.ApiLogger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

/**
 * api log 信息
 *
 * @author Cade Li
 * @since 2023/8/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseLogInfo {
    private ApiLogger apiLogger;
    private Boolean exceptional;
    private HttpServletRequest request;
    private String resultStr;
    private Long timeCost;
}
