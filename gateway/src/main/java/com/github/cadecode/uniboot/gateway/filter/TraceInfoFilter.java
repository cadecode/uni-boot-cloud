package com.github.cadecode.uniboot.gateway.filter;

import cn.hutool.core.lang.UUID;
import com.github.cadecode.uniboot.framework.api.consts.HttpConst;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 用于添加 trace id 的过滤器
 *
 * @author Cade Li
 * @since 2023/8/10
 */
@Order(0)
@Component
public class TraceInfoFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = UUID.fastUUID().toString(true);
        ServerHttpRequest request = exchange.getRequest().mutate()
                .headers(h -> {
                    MDC.put(HttpConst.HEAD_TRACE_ID, traceId);
                    h.set(HttpConst.HEAD_TRACE_ID, traceId);
                })
                .build();
        exchange.getResponse().getHeaders().set(HttpConst.HEAD_TRACE_ID, traceId);
        return chain.filter(exchange.mutate().request(request).build());
    }
}
