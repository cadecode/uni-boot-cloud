package com.github.cadecode.uniboot.framework.svc.convert;

import com.github.cadecode.uniboot.framework.svc.bean.po.SysRole;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.SysRoleAddReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.SysRoleListResVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.SysRoleUpdateReqVo;
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

    SysRoleListResVo poToListResVo(SysRole po);

    List<SysRoleListResVo> poToListResVo(List<SysRole> po);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysRole voToPo(SysRoleUpdateReqVo reqVo);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysRole voToPo(SysRoleAddReqVo reqVo);
}
