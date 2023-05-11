package top.cadecode.uniboot.common.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * java 布尔值和 db 数字类型转换
 *
 * @author Cade Li
 * @since 2023/4/25
 */
@MappedTypes({Boolean.class})
@MappedJdbcTypes({JdbcType.INTEGER})
public class BoolToIntTypeHandler extends BaseTypeHandler<Boolean> {

    /**
     * 用于 mybatis plus update set mapping 参数指定 typeHandler 为 BoolToIntTypeHandler
     */
    public static final String MAPPING = "typeHandler=BoolToIntTypeHandler";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
        if (parameter) {
            ps.setInt(i, 1);
            return;
        }
        ps.setInt(i, 0);
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        return i != 0;
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        return i != 0;
    }

    @Override
    public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        return i != 0;
    }
}
