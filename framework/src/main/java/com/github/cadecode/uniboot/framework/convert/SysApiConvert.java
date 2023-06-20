package com.github.cadecode.uniboot.framework.convert;

import com.github.cadecode.uniboot.framework.bean.po.SysApi;
import com.github.cadecode.uniboot.framework.bean.vo.SysApiVo.SysApiRolesVo;
import com.github.cadecode.uniboot.framework.request.SysApiRequest.SysApiAddRequest;
import com.github.cadecode.uniboot.framework.request.SysApiRequest.SysApiUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

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
