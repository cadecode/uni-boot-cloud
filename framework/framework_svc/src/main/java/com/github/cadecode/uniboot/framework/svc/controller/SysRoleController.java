package com.github.cadecode.uniboot.framework.svc.controller;

import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.common.core.web.response.PageResult;
import com.github.cadecode.uniboot.framework.api.consts.KeyPrefixConst;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysRole;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo;
import com.github.cadecode.uniboot.framework.svc.convert.SysRoleConvert;
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

import static com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.*;

/**
 * 角色管理API
 *
 * @author Cade Li
 * @since 2023/5/4
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "角色管理")
@RequestMapping("system/role")
@RestController
@Validated
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @ApiOperation("查询角色列表")
    @PostMapping("list")
    public List<SysRoleListResVo> roleList(@RequestBody @Valid SysRoleListReqVo reqVo) {
        List<SysRole> roleList = sysRoleService.lambdaQuery()
                .eq(ObjUtil.isNotEmpty(reqVo.getCode()), SysRole::getCode, reqVo.getCode())
                .eq(ObjUtil.isNotEmpty(reqVo.getName()), SysRole::getName, reqVo.getName())
                .eq(SysRole::getType, reqVo.getType())
                .list();
        return SysRoleConvert.INSTANCE.poToListResVo(roleList);
    }

    @ApiOperation("添加用户角色绑定")
    @PostMapping("add_user_mapping")
    public boolean addUserMapping(@RequestBody @NotEmpty List<SysRoleMappingReqVo> reqVoList) {
        return sysRoleService.addRoleUser(reqVoList) > 0;
    }

    @ApiOperation("删除用户角色绑定")
    @PostMapping("remove_user_mapping")
    public boolean removeUserMapping(@RequestBody @NotEmpty List<SysRoleMappingReqVo> reqVoList) {
        return sysRoleService.removeRoleUser(reqVoList) > 0;
    }

    @ApiOperation("添加菜单角色绑定")
    @PostMapping("add_menu_mapping")
    public boolean addMenuMapping(@RequestBody @NotEmpty List<SysRoleMappingReqVo> reqVoList) {
        return sysRoleService.addRoleMenu(reqVoList) > 0;
    }

    @ApiOperation("删除菜单角色绑定")
    @PostMapping("remove_menu_mapping")
    public boolean removeMenuMapping(@RequestBody @NotEmpty List<SysRoleMappingReqVo> reqVoList) {
        return sysRoleService.removeRoleMenu(reqVoList) > 0;
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'ALL'")
    @ApiOperation("添加API角色绑定")
    @PostMapping("add_api_mapping")
    public boolean addApiMapping(@RequestBody @NotEmpty List<SysRoleMappingReqVo> reqVoList) {
        return sysRoleService.addRoleApi(reqVoList) > 0;
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'ALL'")
    @ApiOperation("删除API角色绑定")
    @PostMapping("remove_api_mapping")
    public boolean removeApiMapping(@RequestBody @NotEmpty List<SysRoleMappingReqVo> reqVoList) {
        return sysRoleService.removeRoleApi(reqVoList) > 0;
    }

    @ApiOperation("查询角色列表（带菜单、api）")
    @PostMapping("page_union_vo")
    public PageResult<SysRoleUnionResVo> pageUnionVo(@RequestBody @Valid SysRoleUnionReqVo reqVo) {
        PageInfo<SysRoleUnionResVo> page = sysRoleService.pageUnionVo(reqVo);
        return new PageResult<>((int) page.getTotal(), page.getList());
    }

    @ApiOperation("查询角色列表（带菜单、api）byRoleIds")
    @PostMapping("list_union_vo_by_role_ids")
    public List<SysRoleUnionResVo> listUnionVoByRoleIds(@RequestBody @NotEmpty List<Long> roleIds) {
        return sysRoleService.listUnionVoByRoleIds(roleIds);
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'ALL'")
    @ApiOperation("更新角色")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid SysRoleVo.SysRoleUpdateReqVo reqVo) {
        SysRole po = SysRoleConvert.INSTANCE.voToPo(reqVo);
        return sysRoleService.updateById(po);
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'ALL'")
    @ApiOperation("添加角色")
    @PostMapping("add")
    public boolean add(@RequestBody @Valid SysRoleAddReqVo reqVo) {
        SysRole sysRole = SysRoleConvert.INSTANCE.voToPo(reqVo);
        return sysRoleService.save(sysRole);
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'ALL'")
    @ApiOperation("删除角色（多选）")
    @PostMapping("delete")
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(@RequestBody @NotEmpty List<Long> roleIdList) {
        // 清理角色绑定关系
        sysRoleService.removeRoleApiByRoleIds(roleIdList);
        sysRoleService.removeRoleMenuByRoleIds(roleIdList);
        sysRoleService.removeRoleUserByRoleIds(roleIdList);
        return sysRoleService.removeBatchByIds(roleIdList);
    }
}
