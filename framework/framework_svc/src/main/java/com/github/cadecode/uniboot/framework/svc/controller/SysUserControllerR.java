package com.github.cadecode.uniboot.framework.svc.controller;

import com.github.cadecode.uniboot.common.core.util.AssertUtil;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.base.annotation.ApiInner;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysUserVo.SysUserRolesReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysUserVo.SysUserRolesResVo;
import com.github.cadecode.uniboot.framework.svc.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理API
 *
 * @author Cade Li
 * @since 2023/4/12
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "用户管理-RPC")
@RequestMapping("system/user/r")
@RestController
@Validated
public class SysUserControllerR {

    private final SysUserService sysUserService;

    @ApiInner(onlyClient = true)
    @ApiOperation("查询用户信息")
    @PostMapping("get_by_username")
    public SysUserRolesResVo getByUsername(@RequestParam String username) {
        // 根据用户名查询
        SysUserRolesReqVo reqVo = new SysUserRolesReqVo();
        reqVo.setUsername(username);
        List<SysUserRolesResVo> voList = sysUserService.listRolesVo(reqVo);
        AssertUtil.isEmpty(voList, "此用户名不存在");
        return voList.get(0);
    }
}
