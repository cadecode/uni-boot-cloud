package com.github.cadecode.uniboot.framework.svc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysRole;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.SysRoleMappingReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.SysRoleUnionReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.SysRoleUnionResVo;
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

    int removeRoleUser(List<SysRoleMappingReqVo> list);

    int removeRoleMenu(List<SysRoleMappingReqVo> list);

    int removeRoleApi(List<SysRoleMappingReqVo> list);

    int addRoleUser(List<SysRoleMappingReqVo> list);

    int addRoleMenu(List<SysRoleMappingReqVo> list);

    int addRoleApi(List<SysRoleMappingReqVo> list);

    List<SysRoleUnionResVo> listUnionVo(SysRoleUnionReqVo reqVo);

    PageInfo<SysRoleUnionResVo> pageUnionVo(SysRoleUnionReqVo reqVo);

    List<SysRoleUnionResVo> listUnionVoByRoleIds(List<Long> roleIds);
}
