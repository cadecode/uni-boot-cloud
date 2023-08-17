package com.github.cadecode.uniboot.common.plugin.swagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Swagger2 统一配置类
 *
 * @author Cade Li
 * @date 2021/8/23
 */
@Data
@ConfigurationProperties("uni-boot.swagger")
public class SwaggerProperties {

    private boolean enable;

    /**
     * 模块和包名 MAP
     */
    private Map<String, String> module;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档描述
     */
    private String description;

    /**
     * 联系人
     */
    private String name;

    /**
     * 项目地址
     */
    private String url;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 版本
     */
    private String version;

}
