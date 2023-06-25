package com.github.cadecode.uniboot.framework.security.handler;

import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyService;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserDetailsDto;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录成功处理服务
 *
 * @author Cade Li
 * @since 2023/6/25
 */
public abstract class LoginSuccessHandleService implements StrategyService {

    public abstract ApiResult<SysUserDetailsDto> getResult(HttpServletRequest request, HttpServletResponse response, Authentication authentication);

}
