package top.cadecode.common.core.response;

import lombok.Getter;

/**
 * @author Cade Li
 * @date 2021/12/9
 * @description 响应码接口
 */
public interface ResponseCode {

    // 执行成功的响应码
    ResponseCode SUCCESS = new ResponseCode() {
        @Override
        public String getCode() {
            return "SUCCESS";
        }

        @Override
        public String getReason() {
            return "执行成功";
        }
    };

    // 未知错误的响应码
    ResponseCode UNKNOWN = new ResponseCode() {
        @Override
        public String getCode() {
            return "UNKNOWN";
        }

        @Override
        public String getReason() {
            return "未知错误";
        }
    };

    String getCode();

    String getReason();


    /**
     * 异常枚举类 Code 前缀
     */
    @Getter
    enum CodePrefixEnum {

        AUTH("A"),
        CLIENT("C"),
        FRAME("F"),
        SERVICE("S"),
        THIRD_PARTY("T"),
        ;

        private final String prefix;

        CodePrefixEnum(String prefix) {
            this.prefix = prefix;
        }
    }
}
