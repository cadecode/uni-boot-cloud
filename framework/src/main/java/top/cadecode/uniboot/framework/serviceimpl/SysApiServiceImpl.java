package top.cadecode.uniboot.framework.serviceimpl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.cadecode.uniboot.common.plugin.cache.util.RedisUtil;
import top.cadecode.uniboot.framework.bean.po.SysApi;
import top.cadecode.uniboot.framework.bean.vo.SysApiVo.SysApiRolesVo;
import top.cadecode.uniboot.framework.consts.KeyPrefix;
import top.cadecode.uniboot.framework.mapper.SysApiMapper;
import top.cadecode.uniboot.framework.request.SysApiRequest.SysApiRolesRequest;
import top.cadecode.uniboot.framework.service.SysApiService;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Cacheable(cacheNames = KeyPrefix.API_ROLES, cacheManager = "caffeine5s")
    @Override
    public List<SysApiRolesVo> listRolesVo() {
        List<SysApiRolesVo> sysApiRolesVos = RedisUtil.get(KeyPrefix.API_ROLES, new TypeReference<List<SysApiRolesVo>>() {});
        if (ObjectUtil.isNotNull(sysApiRolesVos)) {
            return sysApiRolesVos;
        }
        synchronized (this) {
            sysApiRolesVos = RedisUtil.get(KeyPrefix.API_ROLES, new TypeReference<List<SysApiRolesVo>>() {});
            if (ObjectUtil.isNotNull(sysApiRolesVos)) {
                return sysApiRolesVos;
            }
            List<SysApiRolesVo> voList = sysApiMapper.selectRolesVo(null);
            // 每 5 分钟刷新一次
            RedisUtil.set(KeyPrefix.API_ROLES, voList, 5, TimeUnit.MINUTES);
            return voList;
        }
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
