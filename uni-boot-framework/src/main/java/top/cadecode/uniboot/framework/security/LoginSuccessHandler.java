package top.cadecode.uniboot.framework.security;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import top.cadecode.uniboot.common.enums.AuthModelEnum;

/**
 * 登录成功处理器抽象
 *
 * @author Cade Li
 * @date 2022/5/28
 */
public abstract class LoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 返回当前模式，用于策略模式
     */
    public abstract AuthModelEnum getAuthModel();
}
