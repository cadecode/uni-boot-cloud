package com.github.cadecode.uniboot.framework.api.feignclient;

import com.github.cadecode.uniboot.framework.api.bean.dto.SysApiDto.SysApiRolesResDto;
import com.github.cadecode.uniboot.framework.api.consts.KeyPrefixConst;
import com.github.cadecode.uniboot.framework.api.consts.SvcNameConst;
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
@FeignClient(contextId = "SysApiClient", name = SvcNameConst.FRAMEWORK)
public interface SysApiClient {

    @Cacheable(cacheNames = KeyPrefixConst.API_ROLES, key = "'ALL'")
    @PostMapping("system/api/list_roles_vo")
    List<SysApiRolesResDto> listRolesResVo();

}
