package com.github.cadecode.uniboot.framework.svc.convert;

import com.github.cadecode.uniboot.framework.svc.bean.po.SysLog;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysLogVo.SysLogPageResVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysLogVo.SysLogSaveReqVo;
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

    List<SysLogPageResVo> poToVo(List<SysLog> records);

    List<SysLog> voToPo(List<SysLogSaveReqVo> requestList);
}
