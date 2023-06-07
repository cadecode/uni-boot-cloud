package top.cadecode.uniboot.framework.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserDetailsDto;
import top.cadecode.uniboot.framework.bean.po.SysUser;
import top.cadecode.uniboot.framework.bean.vo.SysUserVo.SysUserRolesVo;
import top.cadecode.uniboot.framework.request.SysUserRequest.SysUserAddRequest;
import top.cadecode.uniboot.framework.request.SysUserRequest.SysUserModifyInfoRequest;
import top.cadecode.uniboot.framework.request.SysUserRequest.SysUserUpdateRequest;

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
