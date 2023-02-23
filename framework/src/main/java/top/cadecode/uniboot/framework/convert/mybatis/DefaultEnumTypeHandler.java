package top.cadecode.uniboot.framework.convert.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import top.cadecode.uniboot.framework.convert.EnumConvertor;

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
 * 枚举类实现 EnumConvertor 接口可自定义持久化属性，没有实现 EnumConvertor 的普通枚举将使用 ordinal 进行持久化
 *
 * @author Cade Li
 * @date 2022/6/16
 */
public class DefaultEnumTypeHandler<E extends Enum<?>> extends BaseTypeHandler<E> {

    private final Class<E> type;

    private final Map<Integer, E> enumMap;

    public DefaultEnumTypeHandler(Class<E> type) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("类型不能为空");
        }
        this.type = type;
        E[] enums = type.getEnumConstants();
        if (Objects.isNull(enums)) {
            throw new IllegalArgumentException(type.getSimpleName() + " 不是一个枚举类型");
        }
        enumMap = Arrays.stream(enums).collect(toMap(o -> {
            // 实现 EnumConvertor 的枚举根据 persistBy 持久化
            if (o instanceof EnumConvertor) {
                return ((EnumConvertor) o).persistBy();
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
        throw new IllegalArgumentException("未知的泛型元素：" + type.getSimpleName() + "." + value);
    }

    /**
     * 从 enum 元素中获取 value
     */
    private Integer valueFromEnum(E e) {
        if (e instanceof EnumConvertor) {
            return ((EnumConvertor) e).persistBy();
        }
        return e.ordinal();
    }
}
