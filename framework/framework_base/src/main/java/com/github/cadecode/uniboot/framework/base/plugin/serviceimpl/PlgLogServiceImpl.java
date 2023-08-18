package com.github.cadecode.uniboot.framework.base.plugin.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgLog;
import com.github.cadecode.uniboot.framework.base.plugin.mapper.PlgLogMapper;
import com.github.cadecode.uniboot.framework.base.plugin.service.PlgLogService;
import org.springframework.stereotype.Service;

/**
 * 系统日志服务实现
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Service
public class PlgLogServiceImpl extends ServiceImpl<PlgLogMapper, PlgLog> implements PlgLogService {
}
