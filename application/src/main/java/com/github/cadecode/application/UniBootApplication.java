package com.github.cadecode.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 启动类
 */
@MapperScan("com.github.cadecode.**.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class UniBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(UniBootApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UniBootApplication.class);
    }
}
