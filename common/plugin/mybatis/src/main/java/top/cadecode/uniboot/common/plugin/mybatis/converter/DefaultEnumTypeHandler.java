package top.cadecode.uniboot.common.plugin.mybatis.converter;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

/**
 * MyBatis 枚举类解析器，需要配置成默认枚举解析器使用，
 * 枚举类实现 EnumDbConvertor 接口可自定义持久化属性，没有实现 EnumDbConvertor 的普通枚举将使用 ordinal 进行持久化
 * 使用 mybatis.configuration.default-enum-type-handler 来配置
 *
 * @author Cade Li
 * @date 2022/6/16
 */
public class DefaultEnumTypeHandler<E extends Enum<?>> extends BaseTypeHandler<E> {

    private final Class<E> type;

    private final Map<Integer, E> enumMap;

    public DefaultEnumTypeHandler(Class<E> type) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("type can not be null");
        }
        this.type = type;
        E[] enums = type.getEnumConstants();
        if (Objects.isNull(enums)) {
            throw new IllegalArgumentException(type.getSimpleName() + " is not an enum type");
        }
        enumMap = Arrays.stream(enums).collect(toMap(o -> {
            // 实现 EnumDbConvertor 的枚举根据 persistBy 持久化
            if (o instanceof EnumDbConvertor) {
                return ((EnumDbConvertor) o).persistBy();
            }
            return o.ordinal();
        }, o -> o, (p, n) -> p));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        // 默认将标记按 int 入库
        if (Objects.isNull(jdbcType)) {
            ps.setInt(i, valueFromEnum(parameter));
            return;
        }
        ps.setObject(i, valueFromEnum(parameter), jdbcType.TYPE_CODE);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return enumFromValue(i);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return enumFromValue(i);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return enumFromValue(i);
        }
    }

    /**
     * 从 enum 元素中提取指定元素
     */
    private E enumFromValue(Integer value) {
        E e = enumMap.get(value);
        if (Objects.nonNull(e)) {
            return e;
        }
        throw new IllegalArgumentException("Unknown generic element：" + type.getSimpleName() + "." + value);
    }

    /**
     * 从 enum 元素中获取 value
     */
    private Integer valueFromEnum(E e) {
        if (e instanceof EnumDbConvertor) {
            return ((EnumDbConvertor) e).persistBy();
        }
        return e.ordinal();
    }
}
