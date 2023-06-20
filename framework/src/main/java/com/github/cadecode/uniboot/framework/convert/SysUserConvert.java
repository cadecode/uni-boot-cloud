package com.github.cadecode.uniboot.framework.convert;

import com.github.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserDetailsDto;
import com.github.cadecode.uniboot.framework.bean.po.SysUser;
import com.github.cadecode.uniboot.framework.bean.vo.SysUserVo.SysUserRolesVo;
import com.github.cadecode.uniboot.framework.request.SysUserRequest.SysUserAddRequest;
import com.github.cadecode.uniboot.framework.request.SysUserRequest.SysUserModifyInfoRequest;
import com.github.cadecode.uniboot.framework.request.SysUserRequest.SysUserUpdateRequest;
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
