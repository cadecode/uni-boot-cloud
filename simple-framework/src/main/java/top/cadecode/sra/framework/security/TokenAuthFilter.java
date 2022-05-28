package top.cadecode.sra.framework.security;

import org.springframework.web.filter.OncePerRequestFilter;
import top.cadecode.sra.common.enums.AuthModelEnum;

/**
 * @author Cade Li
 * @date 2022/5/28
 * @description Token 校验过滤器抽象，继承 OncePerRequestFilter
 */
public abstract class TokenAuthFilter extends OncePerRequestFilter {

    /**
     * 返回当前模式，用于策略模式
     */
    public abstract AuthModelEnum getAuthModel();
}
