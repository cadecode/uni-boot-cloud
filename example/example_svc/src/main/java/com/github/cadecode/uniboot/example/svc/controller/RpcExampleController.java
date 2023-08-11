package com.github.cadecode.uniboot.example.svc.controller;

import com.github.cadecode.uniboot.framework.api.feignclient.ExampleClient;
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
 * RPC 测试
 *
 * @author Cade Li
 * @since 2023/7/29
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "RPC 测试")
@RestController
@RequestMapping("rpc_example")
public class RpcExampleController {

    private final ExampleClient exampleClient;

    @ApiOperation("传递字符串")
    @PostMapping("test_str")
    public String testStr(@RequestParam String str) {
        return exampleClient.testStr(str);
    }

    @ApiOperation("返回异常结果")
    @PostMapping("test_result")
    public Object testApiResult() {
        return exampleClient.testApiResult();
    }

    @ApiOperation("直接抛出异常")
    @PostMapping("test_exception")
    public Object testException() {
        return exampleClient.testException();
    }

}
