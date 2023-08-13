package com.github.cadecode.uniboot.framework.svc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysApi;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiRolesReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiRolesResVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 系统接口服务
 *
 * @author Cade Li
 * @date 2022/5/27
 */
public interface SysApiService extends IService<SysApi> {

    List<SysApiRolesResVo> listRolesVo();

    List<SysApiRolesResVo> listRolesVoByApiIds(List<Long> userIds);

    List<SysApiRolesResVo> listRolesVo(SysApiRolesReqVo reqVo);

    PageInfo<SysApiRolesResVo> pageRolesVo(SysApiRolesReqVo reqVo);
}
