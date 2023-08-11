package com.github.cadecode.uniboot.framework.api.convert;

import com.github.cadecode.uniboot.framework.api.bean.po.SysUser;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysUserVo.SysUserRolesVo;
import com.github.cadecode.uniboot.framework.api.request.SysUserRequest.SysUserAddRequest;
import com.github.cadecode.uniboot.framework.api.request.SysUserRequest.SysUserModifyInfoRequest;
import com.github.cadecode.uniboot.framework.api.request.SysUserRequest.SysUserUpdateRequest;
import com.github.cadecode.uniboot.framework.api.security.model.SysUserDetails;
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

    SysUserDetails voToSysUserDetails(SysUserRolesVo vo);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "loginIp", ignore = true)
    @Mapping(target = "loginDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysUser requestToPo(SysUserAddRequest request);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "loginIp", ignore = true)
    @Mapping(target = "loginDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enableFlag", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysUser requestToPo(SysUserModifyInfoRequest request);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "loginIp", ignore = true)
    @Mapping(target = "loginDate", ignore = true)
    @Mapping(target = "enableFlag", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysUser requestToPo(SysUserUpdateRequest request);
}
