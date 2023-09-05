package com.github.cadecode.uniboot.framework.svc.controller;

import com.github.cadecode.uniboot.common.core.web.response.PageResult;
import com.github.cadecode.uniboot.framework.api.consts.KeyPrefixConst;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.base.annotation.ApiInner;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysApi;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiRolesReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiRolesResVo;
import com.github.cadecode.uniboot.framework.svc.convert.SysApiConvert;
import com.github.cadecode.uniboot.framework.svc.service.SysApiService;
import com.github.cadecode.uniboot.framework.svc.service.SysRoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiAddReqVo;
import static com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiUpdateReqVo;

/**
 * API 管理
 *
 * @author Cade Li
 * @date 2023/5/14
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "API 管理")
@RequestMapping("system/api")
@RestController
@Validated
public class SysApiController {

    private final SysApiService sysApiService;
    private final SysRoleService sysRoleService;


    @ApiOperation("查询API列表（带角色）")
    @PostMapping("page_roles_vo")
    public PageResult<SysApiRolesResVo> pageRolesVo(@RequestBody @Valid SysApiRolesReqVo reqVo) {
        PageInfo<SysApiRolesResVo> rolesVoPage = sysApiService.pageRolesVo(reqVo);
        return new PageResult<>((int) rolesVoPage.getTotal(), rolesVoPage.getList());
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'ALL'")
    @ApiOperation("更新API")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid SysApiUpdateReqVo reqVo) {
        SysApi po = SysApiConvert.INSTANCE.voToPo(reqVo);
        return sysApiService.updateById(po);
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'ALL'")
    @ApiOperation("添加API")
    @PostMapping("add")
    public boolean add(@RequestBody @Valid SysApiAddReqVo reqVo) {
        SysApi sysApi = SysApiConvert.INSTANCE.voToPo(reqVo);
        return sysApiService.save(sysApi);
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'ALL'")
    @ApiOperation("删除API（多选）")
    @PostMapping("delete")
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(@RequestBody @NotEmpty List<Long> apiIdList) {
        // 清理API角色绑定关系
        sysRoleService.removeRoleApiByApiIds(apiIdList);
        return sysApiService.removeBatchByIds(apiIdList);
    }

    @ApiOperation("获取API（带角色）byApiIds")
    @PostMapping("list_roles_vo_by_api_ids")
    public List<SysApiRolesResVo> listRolesVoByApiIds(@RequestBody @NotEmpty List<Long> apiIdList) {
        return sysApiService.listRolesVoByApiIds(apiIdList);
    }

    @ApiInner(onlyClient = true)
    @ApiOperation("查询API列表-全部")
    @PostMapping("list_roles_vo")
    public List<SysApiRolesResVo> listRolesVo() {
        return sysApiService.listRolesVo();
    }
}
