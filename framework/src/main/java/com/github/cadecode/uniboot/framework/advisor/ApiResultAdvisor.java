package com.github.cadecode.uniboot.framework.advisor;

import cn.hutool.core.util.ObjectUtil;
import com.github.cadecode.uniboot.common.core.exception.ApiException;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.framework.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.enums.FrameErrorEnum;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * API Result 统一格式化器
 *
 * @author Cade Li
 * @date 2022/5/8
 */
@RestControllerAdvice
public class ApiResultAdvisor implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        // 获取请求路径
        String path = request.getURI().getPath();
        // 判断 body 是否是包装好的 ApiResult
        if (body instanceof ApiResult) {
            ApiResult<?> result = (ApiResult<?>) body;
            // 设置状态码
            response.setStatusCode(HttpStatus.valueOf(result.getStatus()));
            // 统一设置路径
            return result.path(path);
        }
        return apiResultFormat(returnType, body, response);
    }

    /**
     * 根据 @ApiFormat 判断是否需要包装成 ApiResult
     *
     * @param returnType MethodParameter
     * @param body       响应体
     * @return 经过包装的 body
     */
    private Object apiResultFormat(MethodParameter returnType, Object body, ServerHttpResponse response) {
        // 是否需要包装标记
        boolean formatFlag;
        // 获取方法上的 ApiFormat 注解
        ApiFormat formatM = returnType.getMethodAnnotation(ApiFormat.class);
        // 以方法上注解为主
        if (Objects.nonNull(formatM)) {
            formatFlag = formatM.value();
        } else {
            // 获取类上的 ApiFormat 注解
            ApiFormat formatC = returnType.getContainingClass().getAnnotation(ApiFormat.class);
            formatFlag = Objects.nonNull(formatC) && formatC.value();
        }
        // 不需要包装，直接返回
        if (!formatFlag) {
            return body;
        }
        // 设置状态码为 200
        response.setStatusCode(HttpStatus.OK);
        if (body instanceof String) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            // String 类型的 Body 需要返回 String 类型，否则报转换错误
            return JacksonUtil.toJson(ApiResult.ok(body));
        }
        // 对接口返回 null 特殊处理
        if (ObjectUtil.isNull(body)) {
            throw ApiException.of(FrameErrorEnum.RES_BODY_NULL, "");
        }
        return ApiResult.ok(body);
    }
}
