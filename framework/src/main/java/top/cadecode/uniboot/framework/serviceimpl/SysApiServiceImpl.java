package top.cadecode.uniboot.framework.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.cadecode.uniboot.framework.bean.po.SysApi;
import top.cadecode.uniboot.framework.bean.vo.SysApiVo.SysApiRolesVo;
import top.cadecode.uniboot.framework.consts.KeyPrefix;
import top.cadecode.uniboot.framework.mapper.SysApiMapper;
import top.cadecode.uniboot.framework.request.SysApiRequest.SysApiRolesRequest;
import top.cadecode.uniboot.framework.service.SysApiService;

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

    @Cacheable(cacheNames = KeyPrefix.API_ROLES, key = "'all'")
    @Override
    public List<SysApiRolesVo> listRolesVo() {
        return sysApiMapper.selectRolesVo(null);
    }

    @Override
    public List<SysApiRolesVo> listRolesVoByApiIds(List<Long> userIds) {
        return sysApiMapper.selectRolesVoByApiIds(userIds);
    }

    @Override
    public List<SysApiRolesVo> listRolesVo(SysApiRolesRequest request) {
        return sysApiMapper.selectRolesVo(request);
    }

    @Override
    public PageInfo<SysApiRolesVo> pageRolesVo(SysApiRolesRequest request) {
        return PageHelper.startPage(request.getPageNumber(), request.getPageSize())
                .doSelectPageInfo(() -> listRolesVo(request));
    }
}
