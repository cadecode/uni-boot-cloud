package com.github.cadecode.uniboot.framework.base.feign;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cadecode.uniboot.common.core.enums.ApiErrorCode;
import com.github.cadecode.uniboot.common.core.exception.ApiException;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult.ErrorMessage;
import com.github.cadecode.uniboot.framework.api.enums.FrameErrorEnum;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Reader;

/**
 * Feign 异常内容解码器
 *
 * @author Cade Li
 * @since 2023/8/2
 */
@Slf4j
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        ApiResult<?> result;
        try {
            Reader reader = response.body().asReader(CharsetUtil.CHARSET_UTF_8);
            result = objectMapper.readValue(reader, ApiResult.class);
        } catch (Exception e) {
            throw ApiException.of(FrameErrorEnum.RPC_UNWRAP_FAIL, e);
        }
        // 获取 ErrorMessage
        ErrorMessage error = result.getError();
        // 返回默认包装的 FeignException
        if (ObjUtil.isNull(error)) {
            FeignException feignException = FeignException.errorStatus(methodKey, response);
            return ApiException.of(FrameErrorEnum.RPC_UNKNOWN_ERROR, feignException);
        }
        // 构造 ApiErrorCode
        ApiErrorCode errorCode = getErrorCode(result, error);
        // 构造 moreInfo
        String moreInfo = markMoreInfo(error);
        return ApiException.of(errorCode, moreInfo);
    }

    /**
     * 根据 ErrorMessage 构造 ApiErrorCode 对象
     */
    private ApiErrorCode getErrorCode(ApiResult<?> result, ErrorMessage error) {
        return new ApiErrorCode() {
            @Override
            public String getCode() {
                return error.getCode();
            }

            @Override
            public String getMessage() {
                return error.getMessage();
            }

            @Override
            public int getStatus() {
                return result.getStatus();
            }
        };
    }

    /**
     * 获取 moreInfo 字符串，在前面加上 RPC 标记
     */
    private String markMoreInfo(ErrorMessage error) {
        String moreInfo = error.getMoreInfo();
        // 在 moreInfo 前段追加 RPC 标记
        if (!StrUtil.startWith(moreInfo, "[RPC]")) {
            moreInfo = StrUtil.concat(true, "[RPC]", moreInfo);
        }
        return moreInfo;
    }
}
