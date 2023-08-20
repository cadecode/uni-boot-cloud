package com.github.cadecode.uniboot.framework.base.security.voter;

import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.framework.api.bean.dto.SysApiDto.SysApiRolesResDto;
import com.github.cadecode.uniboot.framework.api.feignclient.SysApiClient;
import com.github.cadecode.uniboot.framework.base.security.model.SysUserDetails;
import com.github.cadecode.uniboot.framework.base.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.Comparator;
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

    private final SysApiClient sysApiClient;

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        // 若当前请求没有被认证就弃权
        if (!SecurityUtil.isAuthenticated(authentication)) {
            return ACCESS_ABSTAIN;
        }
        // 获取请求 url
        FilterInvocation fi = (FilterInvocation) object;
        String requestUrl = fi.getRequest().getRequestURI();
        // 获取 api role 的关系列表
        List<SysApiRolesResDto> apiRolesResponseList = sysApiClient.listRolesResVo();
        // 获取用户角色
        SysUserDetails sysUserDetails = SecurityUtil.getUserDetails(authentication);
        List<String> roles = sysUserDetails.getRoles();
        // 获取与 url 相同的配置，不存在与 url 相同配置则使用 spring mvc ant 风格匹配
        SysApiRolesResDto apiRolesResponse = apiRolesResponseList.stream()
                .filter(api -> requestUrl.equals(api.getUrl()))
                .findFirst()
                .orElseGet(() -> {
                    // 按 spring mvc 路径优先级找到优先级最高的配置
                    Comparator<String> comparator = antPathMatcher.getPatternComparator(requestUrl);
                    return apiRolesResponseList.stream()
                            .filter(api -> antPathMatcher.match(api.getUrl(), requestUrl))
                            .min((o1, o2) -> comparator.compare(o1.getUrl(), o2.getUrl()))
                            .orElse(null);
                });
        // 数据库没有配置就弃权
        if (Objects.isNull(apiRolesResponse)) {
            return ACCESS_ABSTAIN;
        }
        // 如果该 api 配置没有关联角色，则通过
        // stream().noneMatch 表示没有符合条件的
        if (apiRolesResponse.getRoles().stream().noneMatch(ObjUtil::isNotNull)) {
            return ACCESS_GRANTED;
        }
        // 取用户 token 内角色和数据库查询出角色的交集
        roles.retainAll(apiRolesResponse.getRoles());
        return roles.size() > 0 ? ACCESS_GRANTED : ACCESS_DENIED;
    }
}
