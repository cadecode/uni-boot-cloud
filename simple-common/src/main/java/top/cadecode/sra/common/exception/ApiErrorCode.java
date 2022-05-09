package top.cadecode.sra.common.exception;

/**
 * @author Cade Li
 * @date 2022/5/8
 * @description API 错误码接口，使用枚举类继承该类，便于统一管理异常信息
 */
public interface ApiErrorCode {

    String getCode();

    String getMessage();

    String UNKNOWN_CODE = "UNKNOWN";

    String UNKNOWN_MESSAGE = "未知错误";

    ApiErrorCode UNKNOWN = new ApiErrorCode() {
        @Override
        public String getCode() {
            return UNKNOWN_CODE;
        }

        @Override
        public String getMessage() {
            return UNKNOWN_MESSAGE;
        }
    };
}
