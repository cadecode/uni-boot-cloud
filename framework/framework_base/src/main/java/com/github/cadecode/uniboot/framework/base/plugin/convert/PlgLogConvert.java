package com.github.cadecode.uniboot.framework.base.plugin.convert;

import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgLog;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgLogVo.PlgLogPageResVo;
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
public interface PlgLogConvert {

    PlgLogConvert INSTANCE = Mappers.getMapper(PlgLogConvert.class);

    List<PlgLogPageResVo> poToVo(List<PlgLog> records);

}
