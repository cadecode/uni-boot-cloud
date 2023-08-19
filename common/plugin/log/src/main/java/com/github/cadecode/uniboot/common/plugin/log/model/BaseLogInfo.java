package com.github.cadecode.uniboot.common.plugin.log.model;

import com.github.cadecode.uniboot.common.plugin.log.annotation.ApiLogger;

import javax.servlet.http.HttpServletRequest;

/**
 * api log 信息
 *
 * @author Cade Li
 * @since 2023/8/13
 */
public interface BaseLogInfo {
    ApiLogger getApiLogger();

    Boolean getExceptional();

    HttpServletRequest getRequest();

    String getResultStr();

    Long getTimeCost();
}
