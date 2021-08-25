package top.cadecode.simple.common.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.cadecode.simple.util.JsonUtil;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 统一接口返回格式
 */
@ControllerAdvice(basePackages = {"top.cadecode.simple.controller"})
public class SimpleResAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // 获取请求路径
        String path = request.getURI().getPath();
        // 根据 body 类型分别处理
        if (body instanceof SimpleRes) {
            return ((SimpleRes) body).path(path);
        }
        // String 类型的 Body 需要返回 String 类型，否则报转换错误
        if (body instanceof String) {
            // 设置统一的 Content-Type
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return JsonUtil.objToStr(SimpleRes.ok(body).path(path));
        }
        return SimpleRes.ok(body).path(path);
    }
}
