package com.github.cadecode.uniboot.framework.svc.controller;

import com.github.cadecode.uniboot.common.core.exception.ApiException;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.framework.api.enums.FrameErrorEnum;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author Cade Li
 * @since 2023/7/29
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "测试接口-RPC")
@RestController
@RequestMapping("example/r")
public class ExampleControllerR {

    @ApiOperation("传递字符串")
    @PostMapping("test_str")
    public String testStr(@RequestParam String str) {
        return str;
    }

    @ApiOperation("返回异常结果")
    @PostMapping("test_result")
    public ApiResult<?> testApiResult() {
        return ApiResult.error(FrameErrorEnum.UNKNOWN).moreInfo("测试返回异常结果");
    }

    @ApiOperation("直接抛出异常")
    @PostMapping("test_exception")
    public Object testException() {
        throw ApiException.of("测试直接抛出异常");
    }
}
