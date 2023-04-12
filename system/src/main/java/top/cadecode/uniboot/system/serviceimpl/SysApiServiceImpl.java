package top.cadecode.uniboot.system.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.cadecode.uniboot.common.consts.CacheKeyPrefix;
import top.cadecode.uniboot.common.util.RedisUtil;
import top.cadecode.uniboot.system.bean.po.SysApi;
import top.cadecode.uniboot.system.bean.vo.SysApiVo.SysApiRolesVo;
import top.cadecode.uniboot.system.mapper.SysApiMapper;
import top.cadecode.uniboot.system.service.SysApiService;

import java.util.List;
import java.util.Optional;
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

    @Cacheable(cacheNames = CacheKeyPrefix.API_ROLES, cacheManager = "localCache5s")
    @Override
    public List<SysApiRolesVo> listSysApiVo() {
        List<SysApiRolesVo> sysApiRolesVos = RedisUtil.get(CacheKeyPrefix.API_ROLES, new TypeReference<List<SysApiRolesVo>>() {
        });
        return Optional.ofNullable(sysApiRolesVos).orElseGet(() -> {
            List<SysApiRolesVo> voList = sysApiMapper.listSysApiVo(null);
            // 每三十分钟刷新一次
            RedisUtil.set(CacheKeyPrefix.API_ROLES, voList, 30, TimeUnit.MINUTES);
            return voList;
        });
    }
}
