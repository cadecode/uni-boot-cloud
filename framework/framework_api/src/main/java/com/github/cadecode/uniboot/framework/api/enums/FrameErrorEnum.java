package com.github.cadecode.uniboot.framework.api.enums;

import com.github.cadecode.uniboot.common.core.enums.ApiErrorCode;
import com.github.cadecode.uniboot.common.core.web.response.ApiStatus;
import lombok.Getter;

/**
 * 框架异常枚举
 *
 * @author Cade Li
 * @date 2022/5/30
 */
@Getter
public enum FrameErrorEnum implements ApiErrorCode {

    // web mvc
    VALIDATED_ERROR(1000, "参数校验不通过") {
        @Override
        public int getStatus() {
            return ApiStatus.BAD_REQUEST;
        }
    },
    REQ_PARAM_INVALID(1001, "请求参数无效") {
        @Override
        public int getStatus() {
            return ApiStatus.BAD_REQUEST;
        }
    },
    REQ_BODY_INVALID(1002, "请求体无效") {
        @Override
        public int getStatus() {
            return ApiStatus.BAD_REQUEST;
        }
    },
    MEDIA_TYPE_NO_SUPPORT(1003, "MediaType 不被支持") {
        @Override
        public int getStatus() {
            return ApiStatus.BAD_REQUEST;
        }
    },
    PARAM_TYPE_CONVERT_ERROR(1004, "参数类型转换错误") {
        @Override
        public int getStatus() {
            return ApiStatus.BAD_REQUEST;
        }
    },
    REQUEST_RATE_LIMITED(1005, "请求已被限流") {
        @Override
        public int getStatus() {
            return ApiStatus.TOO_MANY_REQUESTS;
        }
    },
    // 特殊处理接口返回 null 的情况
    RES_BODY_NULL(1009, "响应体为空"),

    // file
    EXTENSION_NOT_ALLOWED(2001, "上传或下载文件的类型不被允许"),
    UPLOAD_FILE_FAIL(2002, "上传文件失败"),
    FILE_NOT_FOUND(2003, "文件未找到"),

    // cloud
    RPC_UNKNOWN_ERROR(3001, "[RPC]UNKNOWN 异常"),
    RPC_UNWRAP_FAIL(3002, "[RPC]UNWRAP 失败"),
    GW_SVC_NOT_FOUND(3003, "[GW]服务未找到") {
        @Override
        public int getStatus() {
            return ApiStatus.SERVER_UNAVAILABLE;
        }
    },
    GW_RES_STATUS_ERROR(3004, "[GW]响应状态错误"),
    GW_SVC_INTERNAL_ERROR(3005, "[GW]服务内部错误"),

    ;

    private final String code;

    private final String message;

    FrameErrorEnum(int code, String message) {
        this.code = "FRAME_" + code;
        this.message = message;
    }
}
