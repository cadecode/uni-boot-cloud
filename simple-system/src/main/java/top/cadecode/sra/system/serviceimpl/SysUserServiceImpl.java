package top.cadecode.sra.system.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.cadecode.sra.system.bean.po.SysUser;
import top.cadecode.sra.system.mapper.SysUserMapper;
import top.cadecode.sra.system.service.SysUserService;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统用户服务实现
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
