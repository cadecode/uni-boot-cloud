package com.github.cadecode.uniboot.framework.base.plugin.handler;

import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.common.plugin.mybatis.annotation.UseDataScope;
import com.github.cadecode.uniboot.common.plugin.mybatis.config.MybatisConfig;
import com.github.cadecode.uniboot.common.plugin.mybatis.handler.DataScopeResolver;
import com.github.cadecode.uniboot.framework.base.security.model.SysUserDetails;
import com.github.cadecode.uniboot.framework.base.util.SecurityUtil;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 基于用户角色的数据权限范围解析器
 *
 * @author Cade Li
 * @since 2024/5/23
 */
@AutoConfigureBefore(MybatisConfig.class)
@Component(UseDataScope.DEFAULT_RESOLVER_NAME)
public class UserRoleDataScopeResolver implements DataScopeResolver {

    public static final String SCOPE_ROLE_PREFIX = "DATA:";

    @Override
    public List<Object> getScopes() {
        SysUserDetails userDetails = SecurityUtil.getUserDetails(null);
        if (Objects.isNull(userDetails)) {
            return null;
        }
        if (ObjUtil.isEmpty(userDetails.getRoles())) {
            return null;
        }
        return userDetails.getRoles()
                .stream()
                .filter(o -> Objects.nonNull(o) && o.startsWith(SCOPE_ROLE_PREFIX))
                .map(o -> (Object) o.substring(SCOPE_ROLE_PREFIX.length()))
                .collect(Collectors.toList());
    }
}
