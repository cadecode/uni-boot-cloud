package com.github.cadecode.uniboot.framework.api.convert;

import com.github.cadecode.uniboot.framework.api.bean.dto.SysUserDto.SysUserDetailsDto;
import com.github.cadecode.uniboot.framework.api.bean.po.SysUser;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysUserVo.SysUserRolesVo;
import com.github.cadecode.uniboot.framework.api.request.SysUserRequest.SysUserAddRequest;
import com.github.cadecode.uniboot.framework.api.request.SysUserRequest.SysUserModifyInfoRequest;
import com.github.cadecode.uniboot.framework.api.request.SysUserRequest.SysUserUpdateRequest;
import org.mapstruct.Mapper;
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

    SysUserRolesVo poToRolesVo(SysUser po);

    SysUserDetailsDto poToDetailsDto(SysUser po);

    SysUserDetailsDto voToDetailsDto(SysUserRolesVo vo);

    SysUser requestToPo(SysUserAddRequest request);

    SysUser requestToPo(SysUserModifyInfoRequest request);

    SysUser requestToPo(SysUserUpdateRequest request);
}
