package top.cadecode.uniboot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.cadecode.uniboot.system.bean.po.SysRole;

import java.util.List;

/**
 * 系统角色服务
 *
 * @author Cade Li
 * @date 2022/5/27
 */
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> listByUserId( Long userId);

    List<SysRole> listByMenuId(Long menuId);

    List<SysRole> listByApiId(Long apiId);
}
