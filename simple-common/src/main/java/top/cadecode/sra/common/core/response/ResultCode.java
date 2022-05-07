package top.cadecode.sra.common.core.response;

/**
 * @author Cade Li
 * @date 2021/12/9
 * @description 响应码接口
 */
public interface ResultCode {

    // 执行成功的响应码
    ResultCode SUCCESS = new ResultCode() {
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
    ResultCode UNKNOWN = new ResultCode() {
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
    enum PrefixEnum {
        BASE(""),
        UTIL("U"),
        ;

        private final String prefix;

        PrefixEnum(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public String toString() {
            return prefix;
        }
    }
}
