package top.cadecode.sra.system.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.cadecode.sra.system.bean.po.SysApi;
import top.cadecode.sra.system.bean.vo.SysApiVo;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统接口 BEAN 转换
 */
@Mapper
public interface SysApiConvert {

    SysApiConvert INSTANCE = Mappers.getMapper(SysApiConvert.class);

    SysApiVo poToVo(SysApi po);

}
