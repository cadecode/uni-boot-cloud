package top.cadecode.uniboot.business.sample.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cadecode.uniboot.framework.annotation.ApiFormat;

/**
 * dynamic-tp 测试
 *
 * @author Cade Li
 * @date 2023/3/15
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "dynamic-tp 测试")
@RestController
@RequestMapping("demo/tp")
public class DynamicTpController {

    private final AsyncTaskExecutor asyncExecutor;

    @ApiOperation("测试 reject 通知")
    @PostMapping("test_reject")
    public String testReject() {
        // 提交 1000 个任务，测试 reject
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            asyncExecutor.execute(() -> {
                log.info("task {}", finalI);
            });
        }
        return "OK";
    }
}
