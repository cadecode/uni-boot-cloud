package info.cadecode.simple.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cade Li
 * @date 2021/7/15
 * @description: 供测试接口使用的 controller
 */
@RestController
@RequestMapping("api/demo")
public class DemoController {

    @PostMapping("string")
    public String string() {
        return "DemoController OK!";
    }
}
