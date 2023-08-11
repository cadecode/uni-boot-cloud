package com.github.cadecode.uniboot.framework.api.convert;

import com.github.cadecode.uniboot.framework.api.bean.po.SysRole;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysRoleVo.SysRoleListVo;
import com.github.cadecode.uniboot.framework.api.request.SysRoleRequest.SysRoleAddRequest;
import com.github.cadecode.uniboot.framework.api.request.SysRoleRequest.SysRoleUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysRole requestToPo(SysRoleUpdateRequest request);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysRole requestToPo(SysRoleAddRequest request);
}
