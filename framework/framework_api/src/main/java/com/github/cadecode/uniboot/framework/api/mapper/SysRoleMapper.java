package com.github.cadecode.uniboot.framework.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.cadecode.uniboot.framework.api.bean.po.SysRole;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysRoleVo.SysRoleUnionVo;
import com.github.cadecode.uniboot.framework.api.request.SysRoleRequest.SysRoleMappingRequest;
import com.github.cadecode.uniboot.framework.api.request.SysRoleRequest.SysRoleUnionRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色 DAO
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectByUserIds(@Param("userIds") List<Long> userIds);

    List<SysRole> selectByMenuIds(@Param("menuIds") List<Long> menuIds);

    List<SysRole> selectByApiIds(@Param("apiIds") List<Long> apiIds);

    int deleteRoleUserByUserIds(@Param("userIds") List<Long> userIds);

    int deleteRoleUserByRoleIds(@Param("roleIds") List<Long> roleIds);

    int deleteRoleMenuByMenuIds(@Param("menuIds") List<Long> menuIds);

    int deleteRoleMenuByRoleIds(@Param("roleIds") List<Long> roleIds);

    int deleteRoleApiByApiIds(@Param("apiIds") List<Long> apiIds);

    int deleteRoleApiByRoleIds(@Param("roleIds") List<Long> roleIds);

    int deleteRoleUser(List<SysRoleMappingRequest> list);

    int deleteRoleMenu(List<SysRoleMappingRequest> list);

    int deleteRoleApi(List<SysRoleMappingRequest> list);

    int insertRoleUser(List<SysRoleMappingRequest> list);

    int insertRoleMenu(List<SysRoleMappingRequest> list);

    int insertRoleApi(List<SysRoleMappingRequest> list);

    List<SysRoleUnionVo> selectRolesVo(@Param("request") SysRoleUnionRequest request);

    List<SysRoleUnionVo> selectRolesVoByRoleIds(@Param("roleIds") List<Long> roleIds);

}
