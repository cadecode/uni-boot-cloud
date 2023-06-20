package com.github.cadecode.uniboot.framework.convert;

import com.github.cadecode.uniboot.framework.bean.po.SysRole;
import com.github.cadecode.uniboot.framework.bean.vo.SysRoleVo.SysRoleListVo;
import com.github.cadecode.uniboot.framework.request.SysRoleRequest.SysRoleAddRequest;
import com.github.cadecode.uniboot.framework.request.SysRoleRequest.SysRoleUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统角色 BEAN 转换
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysRoleConvert {

    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

    SysRoleListVo poToListVo(SysRole po);

    List<SysRoleListVo> poToListVo(List<SysRole> po);

    SysRole requestToPo(SysRoleUpdateRequest request);

    SysRole requestToPo(SysRoleAddRequest request);
}
