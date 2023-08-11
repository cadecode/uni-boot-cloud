package com.github.cadecode.uniboot.framework.svc.convert;

import com.github.cadecode.uniboot.framework.svc.bean.po.SysLog;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysLogVo.SysLogPageVo;
import com.github.cadecode.uniboot.framework.svc.request.SysLogRequest.SysLogSaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统日志 bean convert
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Mapper
public interface SysLogConvert {

    SysLogConvert INSTANCE = Mappers.getMapper(SysLogConvert.class);

    List<SysLogPageVo> poToVo(List<SysLog> records);

    List<SysLog> requestToPo(List<SysLogSaveRequest> requestList);
}
