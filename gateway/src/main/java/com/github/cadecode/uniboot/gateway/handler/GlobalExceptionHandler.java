package com.github.cadecode.uniboot.gateway.handler;

import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.common.core.enums.ApiErrorCode;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.framework.api.enums.FrameErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关全局异常处理
 *
 * @author Cade Li
 * @since 2023/8/11
 */
@Slf4j
@Order(-1)
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        ApiResult<Object> result = getApiResultFromEx(exchange, ex);
        log.error("[GW]Exception handler=> status:{}, path:{}, msg:{},", result.getStatus(), result.getError().getPath(), result.getError().getMessage(), ex);
        response.setStatusCode(HttpStatus.resolve(result.getStatus()));
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JacksonUtil.toJson(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    private ApiResult<Object> getApiResultFromEx(ServerWebExchange exchange, Throwable ex) {
        String moreInfo = ex.getMessage();
        String path = exchange.getRequest().getPath().toString();
        Integer status = null;
        ApiErrorCode errorCode;
        // 服务未找到 503
        if (ex instanceof NotFoundException) {
            errorCode = FrameErrorEnum.GW_SVC_NOT_FOUND;
        } else if (ex instanceof ResponseStatusException) {
            // 响应状态异常
            errorCode = FrameErrorEnum.GW_RES_STATUS_ERROR;
            ResponseStatusException e = (ResponseStatusException) ex;
            status = e.getStatus().value();
        } else {
            // 其他异常
            errorCode = FrameErrorEnum.GW_SVC_INTERNAL_ERROR;
        }
        ApiResult<Object> result = ApiResult.error(errorCode).moreInfo(moreInfo).path(path);
        if (ObjUtil.isNotNull(status)) {
            result.status(status);
        }
        return result;
    }
}
