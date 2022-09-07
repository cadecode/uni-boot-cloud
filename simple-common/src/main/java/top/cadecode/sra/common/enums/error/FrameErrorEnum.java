package top.cadecode.sra.common.enums.error;

import lombok.Getter;
import top.cadecode.sra.common.exception.ApiErrorCode;
import top.cadecode.sra.common.response.ApiStatus;

/**
 * @author Cade Li
 * @date 2022/5/30
 * @description 框架异常枚举
 */
@Getter
public enum FrameErrorEnum implements ApiErrorCode {

    VALIDATED_ERROR(1, "参数校验不通过") {
        @Override
        public int getStatus() {
            return ApiStatus.BAD_REQUEST;
        }
    },
    REQ_PARAM_INVALID(2, "请求参数无效") {
        @Override
        public int getStatus() {
            return ApiStatus.BAD_REQUEST;
        }
    },
    REQ_BODY_INVALID(3, "请求体无效") {
        @Override
        public int getStatus() {
            return ApiStatus.BAD_REQUEST;
        }
    },
    MEDIA_TYPE_NO_SUPPORT(4, "MediaType 不被支持") {
        @Override
        public int getStatus() {
            return ApiStatus.BAD_REQUEST;
        }
    },
    PARAM_TYPE_CONVERT_ERROR(4, "参数类型转换错误") {
        @Override
        public int getStatus() {
            return ApiStatus.BAD_REQUEST;
        }
    },
    REQUEST_RATE_LIMITED(5, "请求已被限流") {
        @Override
        public int getStatus() {
            return ApiStatus.TOO_MANY_REQUESTS;
        }
    },
    ;

    private final String code;

    private final String message;

    FrameErrorEnum(int code, String message) {
        this.code = "FRAME_" + code;
        this.message = message;
    }
}
