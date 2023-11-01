package com.github.cadecode.uniboot.framework.api.feignclient;

import com.github.cadecode.uniboot.framework.api.bean.dto.SysUserDto.SysUserGetByUsernameResDto;
import com.github.cadecode.uniboot.framework.api.consts.SvcNameConst;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SysApi feign client
 *
 * @author Cade Li
 * @since 2023/7/28
 */
@FeignClient(contextId = "SysUserClient", name = SvcNameConst.FRAMEWORK)
public interface SysUserClient {

    @PostMapping("system/user/client/get_by_username")
    SysUserGetByUsernameResDto getByUsername(@RequestParam("username") String username);

}
