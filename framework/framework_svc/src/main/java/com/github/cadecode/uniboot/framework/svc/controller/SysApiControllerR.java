package com.github.cadecode.uniboot.framework.svc.controller;

import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.base.annotation.ApiInner;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiRolesResVo;
import com.github.cadecode.uniboot.framework.svc.service.SysApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * API 管理
 *
 * @author Cade Li
 * @date 2023/5/14
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "API 管理-RPC")
@RequestMapping("system/api/r")
@RestController
@Validated
public class SysApiControllerR {

    private final SysApiService sysApiService;

    @ApiInner(onlyClient = true)
    @ApiOperation("查询API列表-全部")
    @PostMapping("list_roles_vo")
    public List<SysApiRolesResVo> listRolesVo() {
        return sysApiService.listRolesVo();
    }
}
