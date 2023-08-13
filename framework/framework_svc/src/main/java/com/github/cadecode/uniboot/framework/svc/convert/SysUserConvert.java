package com.github.cadecode.uniboot.framework.svc.convert;

import com.github.cadecode.uniboot.framework.base.security.model.SysUserDetails;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysUser;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysUserVo.SysUserAddReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysUserVo.SysUserModifyInfoReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysUserVo.SysUserRolesResVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysUserVo.SysUserUpdateReqVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 系统用户 BEAN 转换
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserDetails voToSysUserDetails(SysUserRolesResVo vo);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "loginIp", ignore = true)
    @Mapping(target = "loginDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysUser voToPo(SysUserAddReqVo reqVo);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "loginIp", ignore = true)
    @Mapping(target = "loginDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enableFlag", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysUser voToPo(SysUserModifyInfoReqVo reqVo);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "loginIp", ignore = true)
    @Mapping(target = "loginDate", ignore = true)
    @Mapping(target = "enableFlag", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysUser voToPo(SysUserUpdateReqVo reqVo);
}
