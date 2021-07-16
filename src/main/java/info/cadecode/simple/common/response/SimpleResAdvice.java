package info.cadecode.simple.common.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 统一接口返回格式
 */
@ControllerAdvice(basePackages = {"info.cadecode.simple.controller"})
public class SimpleResAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof SimpleRes.ResBuilder) {
            return body;
        }
        if (body instanceof String) {
            return SimpleRes.ok(body).json();
        }
        return SimpleRes.ok(body);
    }
}
