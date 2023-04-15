package top.cadecode.uniboot.framework.security.voter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.cadecode.uniboot.framework.security.TokenAuthHolder;
import top.cadecode.uniboot.system.bean.dto.SysUserDto;
import top.cadecode.uniboot.system.bean.vo.SysApiVo.SysApiRolesVo;
import top.cadecode.uniboot.system.service.SysApiService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 基于数据库内容的角色投票器
 *
 * @author Cade Li
 * @date 2022/5/28
 */
@RequiredArgsConstructor
@Component
public class DataBaseRoleVoter extends RoleVoter {

    // ant 匹配器
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final SysApiService sysApiService;

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (!TokenAuthHolder.isAuthenticated(authentication)) {
            return ACCESS_ABSTAIN;
        }
        // 获取请求 url
        FilterInvocation fi = (FilterInvocation) object;
        String requestUrl = fi.getRequest().getRequestURI();
        // 获取 api role 的关系列表
        List<SysApiRolesVo> sysApiRolesVos = sysApiService.listRolesVo();
        // 获取用户角色
        SysUserDto.SysUserDetailsDto sysUserDetailsDto = TokenAuthHolder.getUserDetails(authentication);
        List<String> roles = sysUserDetailsDto.getRoles();
        // 获取与 url 相同的配置，不存在与 url 相同配置则使用 ant 风格匹配
        SysApiRolesVo sysApiRolesVo = sysApiRolesVos.stream()
                .filter(api -> api.getUrl().equals(requestUrl))
                .findFirst()
                .orElseGet(() -> sysApiRolesVos.stream()
                        .filter(api -> antPathMatcher.match(api.getUrl(), requestUrl))
                        .findFirst().orElse(null));
        // 数据库没有配置就弃权
        if (Objects.isNull(sysApiRolesVo)) {
            return ACCESS_ABSTAIN;
        }
        // 取用户 token 内角色和数据库查询出角色的交集
        roles.retainAll(sysApiRolesVo.getRoles());
        return roles.size() > 0 ? ACCESS_GRANTED : ACCESS_DENIED;
    }
}
