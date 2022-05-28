package top.cadecode.sra.system.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.cadecode.sra.system.bean.po.SysRole;
import top.cadecode.sra.system.bean.vo.SysRoleVo;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统角色 BEAN 转换
 */
@Mapper
public interface SysRoleConvert {

    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

    SysRoleVo poToVo(SysRole po);

}
