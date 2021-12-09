package top.cadecode.framework.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.cadecode.common.annotation.ResponseIgnore;
import top.cadecode.common.core.exception.CommonException;
import top.cadecode.common.core.response.CommonResponse;
import top.cadecode.common.enums.ServiceErrorEnum;
import top.cadecode.common.util.JsonUtil;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 统一接口返回格式
 */
@ControllerAdvice(basePackages = {"top.cadecode"})
public class CommonResponseHandler implements ResponseBodyAdvice<Object> {
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
            throw CommonException.of(ServiceErrorEnum.RES_BODY_INVALID)
                    .errorMsg("接口返回结果为空，path 为 " + path);
        }
        // 判断 body 是否是包装好的 SimpleRes
        if (body instanceof CommonResponse) {
            return ((CommonResponse<?>) body).path(path);
        }
        // 判断 body 是否是包装好的字符串
        if (body instanceof String) {
            // 设置统一的 Content-Type
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            // String 类型的 Body 需要返回 String 类型，否则报转换错误
            return JsonUtil.objToStr(CommonResponse.ok(body).path(path));
        }
        // 判断是否有忽略格式化注解，有则直接返回
        ResponseIgnore resIgnore = returnType.getMethodAnnotation(ResponseIgnore.class);
        if (resIgnore != null && resIgnore.value()) {
            return body;
        }

        return CommonResponse.ok(body).path(path);
    }
}
