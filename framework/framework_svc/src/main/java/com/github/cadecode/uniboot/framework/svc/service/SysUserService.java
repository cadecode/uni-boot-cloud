package com.github.cadecode.uniboot.framework.svc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysUser;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysUserVo.SysUserRolesReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysUserVo.SysUserRolesResVo;
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

    List<SysUserRolesResVo> listRolesVoByUserIds(List<Long> userIds);

    List<SysUserRolesResVo> listRolesVo(SysUserRolesReqVo reqVo);

    PageInfo<SysUserRolesResVo> pageRolesVo(SysUserRolesReqVo reqVo);
}
