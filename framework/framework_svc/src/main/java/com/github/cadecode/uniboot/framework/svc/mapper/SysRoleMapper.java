package com.github.cadecode.uniboot.framework.svc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysRole;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.SysRoleMappingReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.SysRoleUnionReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.SysRoleUnionResVo;
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

    int deleteRoleUser(List<SysRoleMappingReqVo> list);

    int deleteRoleMenu(List<SysRoleMappingReqVo> list);

    int deleteRoleApi(List<SysRoleMappingReqVo> list);

    int insertRoleUser(List<SysRoleMappingReqVo> list);

    int insertRoleMenu(List<SysRoleMappingReqVo> list);

    int insertRoleApi(List<SysRoleMappingReqVo> list);

    List<SysRoleUnionResVo> selectUnionVo(@Param("req") SysRoleUnionReqVo reqVo);

    List<SysRoleUnionResVo> selectUnionVoByRoleIds(@Param("roleIds") List<Long> roleIds);

}
