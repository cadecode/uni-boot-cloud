package com.github.cadecode.uniboot.framework.svc.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysLog;
import com.github.cadecode.uniboot.framework.svc.mapper.SysLogMapper;
import com.github.cadecode.uniboot.framework.svc.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * 系统日志服务实现
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
}
