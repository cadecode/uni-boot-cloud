package com.github.cadecode.uniboot.framework.api.feign;

import com.github.cadecode.uniboot.framework.api.bean.vo.SysApiVo.SysApiRolesVo;
import com.github.cadecode.uniboot.framework.api.consts.KeyPrefix;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * SysApi feign client
 *
 * @author Cade Li
 * @since 2023/7/28
 */
@FeignClient(contextId = "SysApiClient", name = "uni-boot-framework")
public interface SysApiClient {

    @Cacheable(cacheNames = KeyPrefix.API_ROLES, key = "'all'")
    @PostMapping("system/api/list_roles_vo")
    List<SysApiRolesVo> listRolesVo();

}
