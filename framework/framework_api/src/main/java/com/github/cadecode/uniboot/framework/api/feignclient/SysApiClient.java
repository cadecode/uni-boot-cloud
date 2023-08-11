package com.github.cadecode.uniboot.framework.api.feignclient;

import com.github.cadecode.uniboot.framework.api.consts.KeyPrefix;
import com.github.cadecode.uniboot.framework.api.consts.SvcName;
import com.github.cadecode.uniboot.framework.api.response.SysApiResponse.SysApiRolesResponse;
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
@FeignClient(contextId = "SysApiClient", name = SvcName.FRAMEWORK)
public interface SysApiClient {

    @Cacheable(cacheNames = KeyPrefix.API_ROLES, key = "'all'")
    @PostMapping("system/api/list_roles_vo")
    List<SysApiRolesResponse> listRolesVo();

}
