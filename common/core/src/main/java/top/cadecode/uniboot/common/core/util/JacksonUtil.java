package top.cadecode.uniboot.common.core.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Jackson 工具类
 *
 * @author Cade Li
 * @date 2022/5/23
 */
@Slf4j
@Component
public class JacksonUtil implements InitializingBean {

    public static ObjectMapper OBJECT_MAPPER;

    private ObjectMapper objectMapper;

    /**
     * 注入 Spring 管理的 ObjectMapper
     */
    @Autowired(required = false)
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 转换对象到 json 串
     *
     * @param bean 需要转换的对象
     * @return json 字符串
     */
    public static String toJson(Object bean) {
        return toJson(bean, false);
    }

    /**
     * 转换对象到 json 串
     *
     * @param bean     需要转换的对象
     * @param isPretty 是否美化输出（换行）
     * @return json 字符串
     */
    public static String toJson(Object bean, boolean isPretty) {
        if (bean == null) {
            return null;
        }
        if (bean instanceof String) {
            return (String) bean;
        }
        try {
            if (!isPretty) {
                return OBJECT_MAPPER.writeValueAsString(bean);
            }
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("cast bean to json fail", e);
        }
    }

    /**
     * 转换字符串到自定义对象
     *
     * @param json  需要转换的字符串
     * @param clazz 自定义对象的 class 对象
     * @return 自定义对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String json, Class<T> clazz) {
        if (StrUtil.isEmpty(json) || Objects.isNull(clazz)) {
            return null;
        }
        if (clazz.equals(String.class)) {
            return (T) json;
        }
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("cast json to bean fail", e);
        }
    }

    /**
     * 转换字符串到集合对象，可以保留泛型
     *
     * @param json          需要转换的字符串
     * @param typeReference 自定义对象的 TypeReference 对象
     * @return 自定义对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String json, TypeReference<T> typeReference) {
        if (StrUtil.isEmpty(json) || Objects.isNull(typeReference)) {
            return null;
        }
        if (typeReference.getType().equals(String.class)) {
            return (T) json;
        }
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("cast json to bean fail", e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        OBJECT_MAPPER = this.objectMapper;
        if (Objects.isNull(OBJECT_MAPPER)) {
            log.warn("Bean objectMapper not found, use default config by JacksonUtil");
            OBJECT_MAPPER = new ObjectMapper();
            // 定义日期格式
            String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
            // Jackson 详细
            // 列入对象的全部字段
            OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            // 取消默认转换 timestamps 形式
            OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            // 忽略空Bean转json的错误
            OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            // 统一所有的日期格式为以下的样式，即 yyyy-MM-dd HH:mm:ss
            OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
            // 忽略在 json 字符串中存在，但是在 java 对象中不存在对应属性的情况。防止错误
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }
}
