package top.cadecode.uniboot.framework.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.cadecode.uniboot.framework.bean.po.SysLog;
import top.cadecode.uniboot.framework.mapper.SysLogMapper;
import top.cadecode.uniboot.framework.service.SysLogService;

/**
 * 系统日志服务实现
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
}
