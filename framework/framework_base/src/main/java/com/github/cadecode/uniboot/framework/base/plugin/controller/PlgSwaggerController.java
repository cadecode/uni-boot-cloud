package com.github.cadecode.uniboot.framework.base.plugin.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgSwaggerVo.PlgSwaggerDescResVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Swagger 插件 API
 *
 * @author Cade Li
 * @since 2023/8/19
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "Swagger 插件")
@RequestMapping("plugin/swagger")
@RestController
@Validated
public class PlgSwaggerController {

    /**
     * 获取全部接口的处理器 mapping
     */
    private final RequestMappingHandlerMapping handlerMapping;

    @ApiOperation("获取全部接口及注解")
    @PostMapping("list_desc_vo")
    public List<PlgSwaggerDescResVo> listSwaggerVo() {
        Map<RequestMappingInfo, HandlerMethod> methodMap = handlerMapping.getHandlerMethods();
        return methodMap.entrySet()
                .stream()
                .map(e -> {
                    ArrayList<String> urlList = new ArrayList<>(e.getKey().getPatternsCondition().getPatterns());
                    String url = null;
                    if (ObjectUtil.isNotEmpty(urlList)) {
                        url = urlList.get(0);
                    }
                    ApiOperation operation = e.getValue().getMethod().getAnnotation(ApiOperation.class);
                    String description = null;
                    if (ObjectUtil.isNotNull(operation)) {
                        description = operation.value();
                    }
                    return PlgSwaggerDescResVo.builder().url(url).description(description).build();
                })
                .filter(o -> ObjectUtil.isNotEmpty(o.getUrl()))
                .distinct()
                .sorted(Comparator.comparing(PlgSwaggerDescResVo::getUrl))
                .collect(Collectors.toList());
    }
}
