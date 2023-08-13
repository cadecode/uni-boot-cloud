package com.github.cadecode.uniboot.framework.svc.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysApi;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiRolesReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiRolesResVo;
import com.github.cadecode.uniboot.framework.svc.mapper.SysApiMapper;
import com.github.cadecode.uniboot.framework.svc.service.SysApiService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统接口服务实现
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@RequiredArgsConstructor
@Service
public class SysApiServiceImpl extends ServiceImpl<SysApiMapper, SysApi> implements SysApiService {

    private final SysApiMapper sysApiMapper;

    @Override
    public List<SysApiRolesResVo> listRolesVo() {
        return sysApiMapper.selectRolesVo(null);
    }

    @Override
    public List<SysApiRolesResVo> listRolesVoByApiIds(List<Long> userIds) {
        return sysApiMapper.selectRolesVoByApiIds(userIds);
    }

    @Override
    public List<SysApiRolesResVo> listRolesVo(SysApiRolesReqVo reqVo) {
        return sysApiMapper.selectRolesVo(reqVo);
    }

    @Override
    public PageInfo<SysApiRolesResVo> pageRolesVo(SysApiRolesReqVo reqVo) {
        return PageHelper.startPage(reqVo.getPageNumber(), reqVo.getPageSize())
                .doSelectPageInfo(() -> listRolesVo(reqVo));
    }
}
