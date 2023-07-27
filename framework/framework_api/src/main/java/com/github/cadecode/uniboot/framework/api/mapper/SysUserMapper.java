package com.github.cadecode.uniboot.framework.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.cadecode.uniboot.framework.api.bean.po.SysUser;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysUserVo.SysUserRolesVo;
import com.github.cadecode.uniboot.framework.api.request.SysUserRequest.SysUserRolesRequest;
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

    List<SysUserRolesVo> selectRolesVoByUserIds(@Param("userIds") List<Long> userIds);

    List<SysUserRolesVo> selectRolesVoByUsername(@Param("username") String username);

    List<SysUserRolesVo> selectRolesVo(@Param("request") SysUserRolesRequest request);

}
