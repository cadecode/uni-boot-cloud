package com.github.cadecode.uniboot.common.core.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.cadecode.uniboot.common.core.exception.UtilException;
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

    private static ObjectMapper PROJECT_OBJECT_MAPPER;

    /**
     * ObjectMapper，序列化结果中携带类型
     */
    private static ObjectMapper TYPING_OBJECT_MAPPER;

    /**
     * SpringBoot 项目默认 ObjectMapper Bean
     */
    private ObjectMapper objectMapper;

    /**
     * 注入 Spring 管理的 ObjectMapper
     */
    @Autowired(required = false)
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static ObjectMapper getMapper() {
        return getMapper(false);
    }

    public static ObjectMapper getMapper(boolean typing) {
        if (typing) {
            return TYPING_OBJECT_MAPPER;
        }
        return PROJECT_OBJECT_MAPPER;
    }

    /**
     * 转换对象到 json 串
     *
     * @param bean 需要转换的对象
     * @return json 字符串
     */
    public static String toJson(Object bean) {
        return toJson(bean, false, false);
    }

    /**
     * 转换对象到 json 串
     *
     * @param bean     需要转换的对象
     * @param isPretty 是否美化输出（换行）
     * @return json 字符串
     */
    public static String toJson(Object bean, boolean isPretty) {
        return toJson(bean, false, isPretty);
    }

    /**
     * 转换对象到 json 串
     *
     * @param bean     需要转换的对象
     * @param typing   是否序列化类型
     * @param isPretty 是否美化输出（换行）
     * @return json 字符串
     */
    public static String toJson(Object bean, boolean typing, boolean isPretty) {
        if (bean == null) {
            return null;
        }
        if (bean instanceof String) {
            return (String) bean;
        }
        try {
            if (!isPretty) {
                return getMapper(typing).writeValueAsString(bean);
            }
            return getMapper(typing).writerWithDefaultPrettyPrinter().writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new UtilException("Cast bean to json fail", e);
        }
    }

    /**
     * 转换字符串到自定义对象，指定 class
     *
     * @param json  需要转换的字符串
     * @param clazz 自定义对象的 class 对象
     * @return 自定义对象
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        return toBean(json, false, clazz);
    }

    /**
     * 转换字符串到集合对象，指定泛型类
     *
     * @param json          需要转换的字符串
     * @param typeReference 自定义对象的 TypeReference 对象
     * @return 自定义对象
     */
    public static <T> T toBean(String json, TypeReference<T> typeReference) {
        return toBean(json, false, typeReference);
    }

    /**
     * 转换字符串到自定义对象，序列化类型
     *
     * @param json 需要转换的字符串
     * @return 自定义对象
     */
    public static Object toBean(String json) {
        return toBean(json, true, Object.class);
    }

    /**
     * 转换字符串到自定义对象
     *
     * @param json   需要转换的字符串
     * @param typing 是否序列化类型
     * @param clazz  自定义对象的 class 对象
     * @return 自定义对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String json, boolean typing, Class<T> clazz) {
        if (StrUtil.isEmpty(json) || Objects.isNull(clazz)) {
            return null;
        }
        if (clazz.equals(String.class)) {
            return (T) json;
        }
        try {
            return getMapper(typing).readValue(json, clazz);
        } catch (Exception e) {
            throw new UtilException("Cast json to bean fail", e);
        }
    }

    /**
     * 转换字符串到集合对象，可以保留泛型
     *
     * @param json          需要转换的字符串
     * @param typing        是否序列化类型
     * @param typeReference 自定义对象的 TypeReference 对象
     * @return 自定义对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String json, boolean typing, TypeReference<T> typeReference) {
        if (StrUtil.isEmpty(json) || Objects.isNull(typeReference)) {
            return null;
        }
        if (typeReference.getType().equals(String.class)) {
            return (T) json;
        }
        try {
            return getMapper(typing).readValue(json, typeReference);
        } catch (IOException e) {
            throw new UtilException("Cast json to bean fail", e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        PROJECT_OBJECT_MAPPER = objectMapper;
        if (Objects.isNull(PROJECT_OBJECT_MAPPER)) {
            log.warn("Bean objectMapper not found, use default config by JacksonUtil");
            PROJECT_OBJECT_MAPPER = new ObjectMapper();
            // 定义日期格式
            String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
            // Jackson 详细配置
            // 列入对象的全部字段
            PROJECT_OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            // 取消默认转换 timestamps 形式
            PROJECT_OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            // 忽略空Bean转json的错误
            PROJECT_OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            // 统一所有的日期格式为以下的样式，即 yyyy-MM-dd HH:mm:ss
            PROJECT_OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
            // 忽略在 json 字符串中存在，但是在 java 对象中不存在对应属性的情况。防止错误
            PROJECT_OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        // 启用序列化类型
        TYPING_OBJECT_MAPPER = PROJECT_OBJECT_MAPPER.copy();
        TYPING_OBJECT_MAPPER.activateDefaultTyping(TYPING_OBJECT_MAPPER.getPolymorphicTypeValidator(), DefaultTyping.NON_FINAL, As.PROPERTY);
    }
}
