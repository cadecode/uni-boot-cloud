package top.cadecode.sra.system.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.cadecode.sra.system.bean.po.SysApi;
import top.cadecode.sra.system.mapper.SysApiMapper;
import top.cadecode.sra.system.service.SysApiService;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统接口服务实现
 */
@Service
public class SysApiServiceImpl extends ServiceImpl<SysApiMapper, SysApi> implements SysApiService {
}
