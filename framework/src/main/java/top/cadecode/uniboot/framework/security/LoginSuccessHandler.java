package top.cadecode.uniboot.framework.security;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import top.cadecode.uniboot.common.enums.AuthModelEnum;
import top.cadecode.uniboot.common.response.ApiResult;
import top.cadecode.uniboot.common.util.JacksonUtil;
import top.cadecode.uniboot.system.bean.dto.SysUserDto;
import top.cadecode.uniboot.system.bean.po.SysUser;
import top.cadecode.uniboot.system.service.SysUserService;

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

    public abstract ApiResult<SysUserDto> getResult(HttpServletRequest request, HttpServletResponse response, Authentication authentication);

    /**
     * 更新登录信息
     *
     * @param sysUserDto 系统用户 DTO
     */
    public void updateLoginInfo(SysUserDto sysUserDto) {
        sysUserDto.setLoginIp(NetUtil.getLocalhost().getHostAddress());
        sysUserDto.setLoginDate(new Date());
        // 同步到用户表
        sysUserService.lambdaUpdate()
                .eq(SysUser::getId, sysUserDto.getId())
                .set(SysUser::getLoginIp, sysUserDto.getLoginIp())
                .set(SysUser::getLoginDate, sysUserDto.getLoginDate())
                .update();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ApiResult<SysUserDto> result = getResult(request, response, authentication);
        updateLoginInfo(result.getData());
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
