package com.github.cadecode.uniboot.gateway.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Gateway swagger 聚合配置
 *
 * @author Cade Li
 * @since 2023/7/29
 */
@RequiredArgsConstructor
@EnableKnife4j
@Configuration
public class GatewaySwaggerConfig {

    /**
     * 网关路由
     */
    private final RouteLocator routeLocator;

    private final GatewayProperties gatewayProperties;

    @Primary
    @Bean
    public SwaggerResourcesProvider resourcesProvider() {
        return new SwaggerResourcesProvider() {
            /**
             * 聚合服务接口
             */
            @Override
            public List<SwaggerResource> get() {
                Set<String> routeSet = new HashSet<>();
                // 获取网关中配置的 route
                routeLocator.getRoutes().subscribe(o -> routeSet.add(o.getId()));
                return gatewayProperties.getRoutes().stream()
                        .filter(o -> routeSet.contains(o.getId()))
                        .flatMap(o -> o.getPredicates().stream()
                                .filter(p -> "Path".equalsIgnoreCase(p.getName()))
                                .map(p -> geneSwaggerResource(o.getId(), p.getArgs()))
                                .collect(Collectors.toList())
                                .stream())
                        .collect(Collectors.toList());
            }

        };
    }

    private SwaggerResource geneSwaggerResource(String name, Map<String, String> args) {
        // 获取 swagger2 location
        String location = args.get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", "/v2/api-docs");
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
