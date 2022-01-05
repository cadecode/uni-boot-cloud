package top.cadecode.framework.security.voter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.cadecode.framework.model.service.SecurityApiService;
import top.cadecode.framework.model.vo.SecurityApiVo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cade Li
 * @date 2021/12/15
 * @description 数据库加载权限 api 的投票器
 */
@RequiredArgsConstructor
@Component
public class DbRoleVoter extends RoleVoter {

    // ant 匹配器
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final SecurityApiService securityApiService;

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return ACCESS_DENIED;
        }
        // 获取请求 url
        FilterInvocation fi = (FilterInvocation) object;
        String requestUrl = fi.getRequestUrl();
        // 获取 api role 的关系列表
        List<SecurityApiVo> securityApiVos = securityApiService.listSecurityApiVos();
        // 获取用户角色
        List<String> roles = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toList());
        // 获取与 url 相同的配置，不存在与 url 相同配置则使用 ant 风格匹配
        SecurityApiVo securityApiVo = securityApiVos.stream()
                .filter(api -> api.getUrl().equals(requestUrl))
                .findFirst()
                .orElseGet(() -> securityApiVos.stream()
                        .filter(api -> antPathMatcher.match(api.getUrl(), requestUrl))
                        .findFirst().orElse(null));
        // 数据库没有配置就弃权
        if (securityApiVo == null) {
            return ACCESS_ABSTAIN;
        }
        // 取用户 token 内角色和数据库查询出角色的交集
        roles.retainAll(securityApiVo.getRoles());
        return roles.size() > 0 ? ACCESS_GRANTED : ACCESS_DENIED;
    }
}
