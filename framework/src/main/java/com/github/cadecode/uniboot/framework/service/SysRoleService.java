package com.github.cadecode.uniboot.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cadecode.uniboot.framework.bean.po.SysRole;
import com.github.cadecode.uniboot.framework.bean.vo.SysRoleVo.SysRoleUnionVo;
import com.github.cadecode.uniboot.framework.request.SysRoleRequest.SysRoleMappingRequest;
import com.github.cadecode.uniboot.framework.request.SysRoleRequest.SysRoleUnionRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 系统角色服务
 *
 * @author Cade Li
 * @date 2022/5/27
 */
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> listByUserIds(List<Long> userIds);

    List<SysRole> listByMenuIds(List<Long> menuIds);

    List<SysRole> listByApiIds(List<Long> apiIds);

    int removeRoleUserByUserIds(List<Long> userIds);

    int removeRoleUserByRoleIds(List<Long> roleIds);

    int removeRoleMenuByMenuIds(List<Long> menuIds);

    int removeRoleMenuByRoleIds(List<Long> roleIds);

    int removeRoleApiByApiIds(List<Long> apiIds);

    int removeRoleApiByRoleIds(List<Long> roleIds);

    int removeRoleUser(List<SysRoleMappingRequest> list);

    int removeRoleMenu(List<SysRoleMappingRequest> list);

    int removeRoleApi(List<SysRoleMappingRequest> list);

    int addRoleUser(List<SysRoleMappingRequest> list);

    int addRoleMenu(List<SysRoleMappingRequest> list);

    int addRoleApi(List<SysRoleMappingRequest> list);

    List<SysRoleUnionVo> listUnionVo(SysRoleUnionRequest request);

    PageInfo<SysRoleUnionVo> pageUnionVo(SysRoleUnionRequest request);

    List<SysRoleUnionVo> listUnionVoByRoleIds(List<Long> roleIds);
}
