package top.cadecode.uniboot.framework.security;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import top.cadecode.uniboot.common.core.util.JacksonUtil;
import top.cadecode.uniboot.common.core.web.response.ApiResult;
import top.cadecode.uniboot.framework.bean.dto.SysUserDto;
import top.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserDetailsDto;
import top.cadecode.uniboot.framework.bean.po.SysUser;
import top.cadecode.uniboot.framework.enums.AuthModelEnum;
import top.cadecode.uniboot.framework.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 登录成功处理器抽象
 *
 * @author Cade Li
 * @date 2022/5/28
 */
public abstract class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final SysUserService sysUserService;

    protected LoginSuccessHandler(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 返回当前模式，用于策略模式
     */
    public abstract AuthModelEnum getAuthModel();

    public abstract ApiResult<SysUserDetailsDto> getResult(HttpServletRequest request, HttpServletResponse response, Authentication authentication);

    /**
     * 更新登录信息
     *
     * @param sysUserDetailsDto 系统用户 DTO
     */
    public void updateLoginInfo(SysUserDto.SysUserDetailsDto sysUserDetailsDto) {
        sysUserDetailsDto.setLoginIp(NetUtil.getLocalhost().getHostAddress());
        sysUserDetailsDto.setLoginDate(new Date());
        // 同步到用户表
        sysUserService.lambdaUpdate()
                .eq(SysUser::getId, sysUserDetailsDto.getId())
                .set(SysUser::getLoginIp, sysUserDetailsDto.getLoginIp())
                .set(SysUser::getLoginDate, sysUserDetailsDto.getLoginDate())
                .update();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ApiResult<SysUserDto.SysUserDetailsDto> result = getResult(request, response, authentication);
        updateLoginInfo(result.getData());
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
