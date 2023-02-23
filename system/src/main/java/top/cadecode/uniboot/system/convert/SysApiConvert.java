package top.cadecode.uniboot.system.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.cadecode.uniboot.system.bean.po.SysApi;
import top.cadecode.uniboot.system.bean.vo.SysApiVo;

/**
 * 系统接口 BEAN 转换
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysApiConvert {

    SysApiConvert INSTANCE = Mappers.getMapper(SysApiConvert.class);

    SysApiVo poToVo(SysApi po);

}
