package top.cadecode.business.contorller;

import org.springframework.web.bind.annotation.*;
import top.cadecode.common.core.response.CommonResponse;

import java.util.*;

/**
 * @author Cade Li
 * @date 2021/7/15
 * @description 供测试接口使用的 controller
 */
@RestController
@RequestMapping("api/demo")
public class DemoController {

    @PostMapping("string")
    public String string() {
        return "DemoController OK!";
    }

    @PostMapping("map")
    public Map<String, Object> map() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "map");
        map.put("msg", "hello world");
        return map;
    }

    @PostMapping("res")
    public CommonResponse<List<String>> res() {
        return CommonResponse.ok(Arrays.asList("a", "b"));
    }
}
