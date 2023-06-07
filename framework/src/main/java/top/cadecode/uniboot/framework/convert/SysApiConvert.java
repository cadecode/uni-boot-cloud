package top.cadecode.uniboot.framework.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.cadecode.uniboot.framework.bean.po.SysApi;
import top.cadecode.uniboot.framework.bean.vo.SysApiVo.SysApiRolesVo;
import top.cadecode.uniboot.framework.request.SysApiRequest.SysApiAddRequest;
import top.cadecode.uniboot.framework.request.SysApiRequest.SysApiUpdateRequest;

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
