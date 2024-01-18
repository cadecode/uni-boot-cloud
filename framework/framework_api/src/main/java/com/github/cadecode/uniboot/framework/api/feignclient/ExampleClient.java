package com.github.cadecode.uniboot.framework.api.feignclient;

import com.github.cadecode.uniboot.framework.api.consts.SvcNameConst;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 测试 feign client
 *
 * @author Cade Li
 * @since 2023/7/28
 */
@FeignClient(contextId = "ExampleClient", name = SvcNameConst.FRAMEWORK)
public interface ExampleClient {

    @PostMapping("example/r/test_str")
    String testStr(@RequestParam("str") String str);

    @PostMapping("example/r/test_result")
    Object testApiResult();

    @PostMapping("example/r/test_exception")
    Object testException();
}
