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

    List<SysRole> listByUserIds(List<Long> userIds);

    List<SysRole> listByMenuIds(List<Long> menuIds);

    List<SysRole> listByApiIds(List<Long> apiIds);

    int removeRoleUserByUserIds(List<Long> userIds);

    int removeRoleUserByRoleIds(List<Long> roleIds);

    int removeRoleMenuByMenuIds(List<Long> menuIds);

    int removeRoleMenuByRoleIds(List<Long> roleIds);

    int removeRoleApiByApiIds(List<Long> apiIds);

    int removeRoleApiByRoleIds(List<Long> roleIds);
}
