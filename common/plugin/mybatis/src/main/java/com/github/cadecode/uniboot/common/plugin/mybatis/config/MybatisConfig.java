package com.github.cadecode.uniboot.common.plugin.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.github.cadecode.uniboot.common.plugin.mybatis.aspect.DataScopeAspect;
import com.github.cadecode.uniboot.common.plugin.mybatis.aspect.DataScopeAspect.DataScopeParam;
import com.github.cadecode.uniboot.common.plugin.mybatis.handler.DataScopePermissionHandler;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.Objects;

/**
 * mybatis 配置类
 *
 * @author Cade Li
 * @date 2022/2/16
 */
@Configuration
public class MybatisConfig {

    /**
     * Mybatis Plus 插件配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 数据权限插件
        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor(new DataScopePermissionHandler()) {
            @Override
            public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
                // 没有使用注解时，跳过
                DataScopeParam dataScopeParam = DataScopeAspect.currDataScope();
                if (Objects.isNull(dataScopeParam) || !dataScopeParam.isEnableFilter()) {
                    return;
                }
                super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
            }
        };
        interceptor.addInnerInterceptor(dataPermissionInterceptor);

        // 添加分页插件，动态获取数据库类型
        // interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
