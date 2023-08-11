package com.github.cadecode.uniboot.framework.svc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志 DAO
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
}
