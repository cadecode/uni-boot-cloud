package com.github.cadecode.uniboot.example.svc.controller;

import com.github.cadecode.uniboot.common.plugin.log.annotation.ApiLogger;
import com.github.cadecode.uniboot.framework.api.consts.LogTypeConst;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志测试
 *
 * @author Cade Li
 * @since 2023/8/18
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "日志测试")
@RestController
@RequestMapping("demo/log")
public class LogExampleController {

    @ApiLogger(type = LogTypeConst.QUERY, description = "测试查询log",enableSave = true)
    @ApiOperation("测试查询log")
    @GetMapping("query")
    public String query() {
        return "OK";
    }
}
