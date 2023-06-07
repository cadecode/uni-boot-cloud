package top.cadecode.uniboot.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.cadecode.uniboot.framework.bean.po.SysLog;

/**
 * 系统日志 DAO
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
}
