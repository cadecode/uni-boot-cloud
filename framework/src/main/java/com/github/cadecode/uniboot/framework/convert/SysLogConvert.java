package com.github.cadecode.uniboot.framework.convert;

import com.github.cadecode.uniboot.framework.bean.dto.SysLogDto.SysLogInfoDto;
import com.github.cadecode.uniboot.framework.bean.po.SysLog;
import com.github.cadecode.uniboot.framework.bean.vo.SysLogVo.SysLogPageVo;
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

    SysLog dtoToPo(SysLogInfoDto dto);

    List<SysLogPageVo> poToVo(List<SysLog> records);
}
