package top.cadecode.uniboot.common.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import top.cadecode.uniboot.common.util.JacksonUtil;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * java 对象和 db varchar 转换，即 json 转换
 *
 * @author Cade Li
 * @since 2023/5/10
 */
@MappedTypes({Object.class, List.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ObjToStrTypeHandler extends BaseTypeHandler<Object> {

    private final Class<?> type;

    public ObjToStrTypeHandler(Class<?> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JacksonUtil.toJson(parameter));
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        final String json = rs.getString(columnName);
        return JacksonUtil.toBean(json, type);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        final String json = rs.getString(columnIndex);
        return JacksonUtil.toBean(json, type);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        final String json = cs.getString(columnIndex);
        return JacksonUtil.toBean(json, type);
    }
}
