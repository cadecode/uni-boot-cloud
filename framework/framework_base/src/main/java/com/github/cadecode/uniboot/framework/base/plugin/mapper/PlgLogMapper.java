package com.github.cadecode.uniboot.framework.base.plugin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志 DAO
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Mapper
public interface PlgLogMapper extends BaseMapper<PlgLog> {
}
