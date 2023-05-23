package top.cadecode.uniboot.system.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.cadecode.uniboot.system.bean.po.SysApi;
import top.cadecode.uniboot.system.bean.vo.SysApiVo.SysApiRolesVo;
import top.cadecode.uniboot.system.request.SysApiRequest.SysApiAddRequest;
import top.cadecode.uniboot.system.request.SysApiRequest.SysApiUpdateRequest;

/**
 * 系统接口 BEAN 转换
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysApiConvert {

    SysApiConvert INSTANCE = Mappers.getMapper(SysApiConvert.class);

    SysApiRolesVo poToRolesVo(SysApi po);

    SysApi requestToPo(SysApiUpdateRequest request);

    SysApi requestToPo(SysApiAddRequest request);

}
