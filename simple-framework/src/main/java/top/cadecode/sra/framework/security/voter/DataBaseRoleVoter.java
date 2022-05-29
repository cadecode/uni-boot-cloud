package top.cadecode.sra.framework.security.voter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.cadecode.sra.system.bean.dto.SysUserDto;
import top.cadecode.sra.system.bean.vo.SysApiVo;
import top.cadecode.sra.system.service.SysApiService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Cade Li
 * @date 2022/5/28
 * @description 基于数据库内容的角色投票器
 */
@RequiredArgsConstructor
@Component
public class DataBaseRoleVoter extends RoleVoter {

    // ant 匹配器
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final SysApiService sysApiService;

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return ACCESS_DENIED;
        }
        // 获取请求 url
        FilterInvocation fi = (FilterInvocation) object;
        String requestUrl = fi.getRequest().getRequestURI();
        // 获取 api role 的关系列表
        List<SysApiVo> sysApiVos = sysApiService.listSysApiVo();
        // 获取用户角色
        SysUserDto sysUserDto = (SysUserDto) authentication.getPrincipal();
        List<String> roles = sysUserDto.getRoles();
        // 获取与 url 相同的配置，不存在与 url 相同配置则使用 ant 风格匹配
        SysApiVo sysApiVo = sysApiVos.stream()
                .filter(api -> api.getUrl().equals(requestUrl))
                .findFirst()
                .orElseGet(() -> sysApiVos.stream()
                        .filter(api -> antPathMatcher.match(api.getUrl(), requestUrl))
                        .findFirst().orElse(null));
        // 数据库没有配置就弃权
        if (Objects.isNull(sysApiVo)) {
            return ACCESS_ABSTAIN;
        }
        // 取用户 token 内角色和数据库查询出角色的交集
        roles.retainAll(sysApiVo.getRoles());
        return roles.size() > 0 ? ACCESS_GRANTED : ACCESS_DENIED;
    }
}
