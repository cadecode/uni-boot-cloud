package top.cadecode.sra.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.cadecode.sra.system.bean.po.SysUser;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统用户服务
 */
public interface SysUserService extends IService<SysUser>, UserDetailsService {
}
