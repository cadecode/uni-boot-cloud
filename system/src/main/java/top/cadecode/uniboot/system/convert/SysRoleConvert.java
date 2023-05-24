package top.cadecode.uniboot.system.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.cadecode.uniboot.system.bean.po.SysRole;
import top.cadecode.uniboot.system.bean.vo.SysRoleVo.SysRoleListVo;
import top.cadecode.uniboot.system.request.SysRoleRequest.SysRoleAddRequest;
import top.cadecode.uniboot.system.request.SysRoleRequest.SysRoleUpdateRequest;

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
