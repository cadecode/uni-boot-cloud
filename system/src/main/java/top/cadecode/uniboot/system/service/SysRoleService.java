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

    int deleteRoleUserByUserIds(List<Long> userIds);

    int deleteRoleUserByRoleIds(List<Long> roleIds);

    int deleteRoleMenuByMenuIds(List<Long> menuIds);

    int deleteRoleMenuByRoleIds(List<Long> roleIds);

    int deleteRoleApiByApiIds(List<Long> apiIds);

    int deleteRoleApiByRoleIds(List<Long> roleIds);
}
