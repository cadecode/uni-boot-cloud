package com.github.cadecode.uniboot.common.core.web.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toMap;

/**
 * spring mvc 接口参数字符串转枚举转换器工厂
 *
 * @author Cade Li
 * @date 2022/6/14
 */
public class ParamEnumConvertorFactory implements ConverterFactory<String, ParamEnumConvertor> {

    private static final Map<Class<?>, Converter<String, ?>> CONVERTER_MAP = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ParamEnumConvertor> Converter<String, T> getConverter(Class<T> targetType) {
        Converter<String, ?> stringConverter = CONVERTER_MAP.get(targetType);
        if (Objects.isNull(stringConverter)) {
            stringConverter = new StringEnumConvertor<>(targetType);
            CONVERTER_MAP.put(targetType, stringConverter);
        }
        return (Converter<String, T>) stringConverter;
    }

    /**
     * 字符串转枚举转换器
     */
    public static class StringEnumConvertor<T extends ParamEnumConvertor> implements Converter<String, T> {

        private final Map<String, T> enumMap;

        public StringEnumConvertor(Class<T> targetType) {
            enumMap = Arrays.stream(targetType.getEnumConstants())
                    .collect(toMap(ParamEnumConvertor::convertBy, o -> o, (p, n) -> n));
        }

        @Override
        public T convert(String source) {
            return enumMap.get(source);
        }
    }
}
