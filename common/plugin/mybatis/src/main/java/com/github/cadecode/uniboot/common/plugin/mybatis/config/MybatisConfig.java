package com.github.cadecode.uniboot.common.plugin.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.github.cadecode.uniboot.common.plugin.mybatis.handler.DataScopePermissionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor(new DataScopePermissionHandler());
        interceptor.addInnerInterceptor(dataPermissionInterceptor);

        // 添加分页插件，动态获取数据库类型
        // interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
