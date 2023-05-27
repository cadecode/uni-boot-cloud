package top.cadecode.uniboot.system.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.cadecode.uniboot.system.bean.dto.SysLogDto.SysLogInfoDto;
import top.cadecode.uniboot.system.bean.po.SysLog;
import top.cadecode.uniboot.system.bean.vo.SysLogVo.SysLogPageVo;

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
