package top.cadecode.sra.system.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.cadecode.sra.system.bean.po.SysUser;
import top.cadecode.sra.system.bean.vo.SysUserVo;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统用户 BEAN 转换
 */
@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserVo poToVo(SysUser po);

}
