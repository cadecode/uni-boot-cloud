package top.cadecode.sra.framework.security;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import top.cadecode.sra.common.enums.AuthModelEnum;

/**
 * @author Cade Li
 * @date 2022/5/28
 * @description 登录成功处理器抽象
 */
public abstract class LoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 返回当前模式，用于策略模式
     */
    public abstract AuthModelEnum getAuthModel();
}
