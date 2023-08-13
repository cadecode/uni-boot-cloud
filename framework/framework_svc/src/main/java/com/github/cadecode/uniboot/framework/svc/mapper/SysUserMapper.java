package com.github.cadecode.uniboot.framework.svc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysUser;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysUserVo.SysUserRolesReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysUserVo.SysUserRolesResVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户 DAO
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUserRolesResVo> selectRolesVoByUserIds(@Param("userIds") List<Long> userIds);

    List<SysUserRolesResVo> selectRolesVoByUsername(@Param("username") String username);

    List<SysUserRolesResVo> selectRolesVo(@Param("req") SysUserRolesReqVo reqVo);

}
