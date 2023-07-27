package com.github.cadecode.uniboot.framework.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cadecode.uniboot.framework.api.bean.po.SysUser;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysUserVo.SysUserRolesVo;
import com.github.cadecode.uniboot.framework.api.request.SysUserRequest.SysUserRolesRequest;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

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
