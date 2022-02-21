package top.cadecode.framework.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.cadecode.common.annotation.ResIgnore;
import top.cadecode.common.core.exception.BaseException;
import top.cadecode.common.core.response.Result;
import top.cadecode.common.enums.BaseErrorEnum;
import top.cadecode.common.util.JsonUtil;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 统一接口返回格式
 */
@ControllerAdvice(basePackages = {"top.cadecode"})
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 获取请求路径
        String path = request.getURI().getPath();
        // 判断 body 是否是 null
        if (body == null) {
            throw BaseException.of(BaseErrorEnum.RES_BODY_INVALID);
        }
        // 判断 body 是否是包装好的 SimpleRes
        if (body instanceof Result) {
            return ((Result<?>) body).path(path);
        }
        // 判断 body 是否是包装好的字符串
        if (body instanceof String) {
            // 设置统一的 Content-Type
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            // String 类型的 Body 需要返回 String 类型，否则报转换错误
            return JsonUtil.objToStr(Result.ok(body).path(path));
        }
        // 判断是否有忽略格式化注解，有则直接返回
        ResIgnore resIgnore = returnType.getMethodAnnotation(ResIgnore.class);
        if (resIgnore != null && resIgnore.value()) {
            return body;
        }

        return Result.ok(body).path(path);
    }
}
