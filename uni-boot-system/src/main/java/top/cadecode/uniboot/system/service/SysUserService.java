package top.cadecode.uniboot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.cadecode.uniboot.system.bean.po.SysUser;

/**
 * 系统用户服务
 *
 * @author Cade Li
 * @date 2022/5/27
 */
public interface SysUserService extends IService<SysUser>, UserDetailsService {
}
