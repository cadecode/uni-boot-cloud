package top.cadecode.uniboot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.cadecode.uniboot.system.bean.po.SysUser;
import top.cadecode.uniboot.system.bean.vo.SysUserVo.SysUserRolesVo;
import top.cadecode.uniboot.system.request.SysUserRequest.SysUserRolesRequest;

import java.util.List;

/**
 * 系统用户服务
 *
 * @author Cade Li
 * @date 2022/5/27
 */
public interface SysUserService extends IService<SysUser>, UserDetailsService {

    List<SysUserRolesVo> listRolesVoByUserIds(List<Long> userIds);

    List<SysUserRolesVo> listRolesVo(SysUserRolesRequest request);

    PageInfo<SysUserRolesVo> pageRolesVo(SysUserRolesRequest request);
}
