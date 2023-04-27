package top.cadecode.uniboot.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.cadecode.uniboot.framework.security.TokenAuthHolder;

import java.util.Date;

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
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.setFieldValByName("createTime", new Date(), metaObject);
                updateUser(metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName("updateTime", new Date(), metaObject);
                updateUser(metaObject);
            }

            public void updateUser(MetaObject metaObject) {
                this.setFieldValByName("updateUser", TokenAuthHolder.getUsername(), metaObject);
            }
        };
    }
}
