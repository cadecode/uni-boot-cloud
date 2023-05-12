package top.cadecode.uniboot.demo.controller;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cadecode.uniboot.common.annotation.ApiFormat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 阿里 TransmittableThreadLocal 测试
 *
 * @author Cade Li
 * @date 2023/3/13
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "TransmittableThreadLocal 测试")
@RestController
@RequestMapping("demo/ttl")
public class TtlExecutorController {

    @ApiOperation("测试普通 ThreadLocal")
    @PostMapping("test_thread_local")
    public String testThreadLocal() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("A");
        Executor executor = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                // 前两个线程打印出 null，因为普通的 ThreadLocal 不能被子线程继承
                System.out.println(threadLocal.get());
                threadLocal.set("B");
                // 修改了之后，后续线程被复用时会打印出修改后的值
            });
        }
        return "OK";
    }

    @ApiOperation("测试 TransmittableThreadLocal")
    @PostMapping("test_ttl")
    public String testTransmittableThreadLocal() {
        ThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();
        threadLocal.set("A");
        Executor executor = TtlExecutors.getTtlExecutor(Executors.newFixedThreadPool(2));
        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                System.out.println(threadLocal.get());
                threadLocal.set("B");
                // 使用 ttl 后，5 次都打印出 A，说明 ttl 可以被子线程继承，并且线程复用时没有相互影响
            });
        }
        return "OK";
    }
}
