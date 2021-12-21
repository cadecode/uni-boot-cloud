package top.cadecode.framework.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisSqlPrintingHandler implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取 SQL 描述语句对象
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取参数
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        // 获取 SQL Id
        String sqlId = mappedStatement.getId();
        // 获取 BoundSql 即 mybatis 封装的 SQL 对象
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        // 获取配置
        Configuration configuration = mappedStatement.getConfiguration();
        // 计时，执行
        long start = System.currentTimeMillis();
        Object returnValue = invocation.proceed();
        long time = System.currentTimeMillis() - start;
        // 打印 SQL
        showSql(configuration, boundSql, time, sqlId);
        return returnValue;
    }

    /**
     * 处理 sql 中的字符
     *
     * @param configuration 配置对象
     * @param boundSql      boundSql
     * @param time          用时
     * @param sqlId         sql id
     */
    private static void showSql(Configuration configuration, BoundSql boundSql, long time, String sqlId) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // 替换空格、换行、tab缩进等
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        logs(time, sql, sqlId);
    }

    /**
     * 对不同类型参数进行处理
     *
     * @param obj 参数
     * @return 处理后的参数
     */
    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else if (obj instanceof Date) {
            // 处理日期类型
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            value = "'" + sdf.format(new Date()) + "'";
        } else {
            value = obj != null ? obj.toString() : "";
        }
        return value.replace("$", "\\$");
    }

    /**
     * 打印 log
     *
     * @param time  用时
     * @param sql   sql 语句
     * @param sqlId sql id
     */
    private static void logs(long time, String sql, String sqlId) {
        log.info("sql 日志 => 执行 [" + sqlId + "] 用时 " + time + " 毫秒");
        log.info("sql 日志 <= 语句：" + sql);
    }
}
