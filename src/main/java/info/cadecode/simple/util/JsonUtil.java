package info.cadecode.simple.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author Cade Li
 * @date 2021/7/15
 * @description: 基于 Jackson 的 json 工具类
 */
public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();
    // 定义日期格式
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 配置 Jackson
    static {
        // 列入对象的全部字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 取消默认转换 timestamps 形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 统一所有的日期格式为以下的样式，即 yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        // 忽略在 json 字符串中存在，但是在 java 对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 转换对象到 json 串
     *
     * @param obj 需要转换的对象
     * @return json 字符串
     */
    public static String objToStr(Object obj) {
        return objToStr(obj, false);
    }

    /**
     * 转换对象到 json 串
     *
     * @param obj      需要转换的对象
     * @param isPretty 是否美化输出（换行）
     * @return json 字符串
     */
    public static String objToStr(Object obj, boolean isPretty) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        try {
            if (!isPretty) {
                return objectMapper.writeValueAsString(obj);
            }
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * 转换字符串到自定义对象
     *
     * @param str   需要转换的字符串
     * @param clazz 自定义对象的 class 对象
     * @return 自定义对象
     */
    public static <T> T str2Obj(String str, Class<T> clazz) {
        if (StringUtil.isEmpty(str) || clazz == null) {
            return null;
        }
        if (clazz.equals(String.class)) {
            return (T) str;
        }
        try {
            return objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.error("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * 转换字符串到集合对象，可以保留泛型
     *
     * @param str           需要转换的字符串
     * @param typeReference 自定义对象的 TypeReference 对象
     * @return 自定义对象
     */
    public static <T> T str2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtil.isEmpty(str) || typeReference == null) {
            return null;
        }
        if (typeReference.getType().equals(String.class)) {
            return (T) str;
        }
        try {
            return objectMapper.readValue(str, typeReference);
        } catch (IOException e) {
            log.error("Parse String to Object error", e);
            return null;
        }
    }
}
