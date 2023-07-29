package com.github.cadecode.uniboot.framework.svc.controller;

import com.github.cadecode.uniboot.framework.api.annotation.ApiFormat;
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
@Api(tags = "测试接口")
@RestController
@RequestMapping("example")
public class ExampleController {

    // For feign client
    @ApiFormat(false)
    @ApiOperation("传递字符串")
    @PostMapping("test_str")
    public String testStr(@RequestParam String str) {
        return str;
    }

}
