package com.github.cadecode.uniboot.gateway.filter;

import com.github.cadecode.uniboot.framework.api.consts.HttpConst;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 请求头统一过滤
 *
 * @author Cade Li
 * @since 2023/8/8
 */
@Order(1)
@Component
public class RequestHeaderFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 去掉内部调用专用的请求头，防止伪造
        ServerHttpRequest request = exchange.getRequest().mutate()
                .headers(h -> {
                    h.remove(HttpConst.HEAD_SOURCE);
                    h.remove(HttpConst.HEAD_USER_DETAILS);
                })
                .build();
        return chain.filter(exchange.mutate().request(request).build());
    }
}
