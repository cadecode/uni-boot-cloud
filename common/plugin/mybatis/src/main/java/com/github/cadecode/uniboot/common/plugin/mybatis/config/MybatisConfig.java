package com.github.cadecode.uniboot.common.plugin.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.github.cadecode.uniboot.common.plugin.mybatis.aspect.DataScopeAspect;
import com.github.cadecode.uniboot.common.plugin.mybatis.aspect.DataScopeAspect.DataScopeParam;
import com.github.cadecode.uniboot.common.plugin.mybatis.handler.DataScopePermissionHandler;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * mybatis 配置类
 *
 * @author Cade Li
 * @date 2022/2/16
 */
@RequiredArgsConstructor
@AutoConfigureAfter(PageHelperAutoConfiguration.class)
@Configuration
public class MybatisConfig implements InitializingBean {

    private final List<SqlSessionFactory> sqlSessionFactoryList;

    @Override
    public void afterPropertiesSet() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件，动态获取数据库类型
        // interceptor.addInnerInterceptor(new PaginationInnerInterceptor());

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

        // 加入插件
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            configuration.addInterceptor(interceptor);
        }
    }
}
