package top.cadecode.sra.system.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.cadecode.sra.common.consts.CacheKeyPrefix;
import top.cadecode.sra.common.util.RedisUtil;
import top.cadecode.sra.common.util.RedisUtil.CastRef;
import top.cadecode.sra.system.bean.po.SysApi;
import top.cadecode.sra.system.bean.vo.SysApiVo;
import top.cadecode.sra.system.mapper.SysApiMapper;
import top.cadecode.sra.system.service.SysApiService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统接口服务实现
 */
@RequiredArgsConstructor
@Service
public class SysApiServiceImpl extends ServiceImpl<SysApiMapper, SysApi> implements SysApiService {

    private final SysApiMapper sysApiMapper;

    @Cacheable(cacheNames = CacheKeyPrefix.API_ROLES, cacheManager = "localCache5s")
    @Override
    public List<SysApiVo> listSysApiVo() {
        List<SysApiVo> sysApiVos = RedisUtil.get(CacheKeyPrefix.API_ROLES, new CastRef<>());
        return Optional.ofNullable(sysApiVos)
                .orElseGet(() -> {
                    List<SysApiVo> voList = sysApiMapper.listSysApiVo(null);
                    // 每三十分钟刷新一次
                    RedisUtil.set(CacheKeyPrefix.API_ROLES, voList, 30, TimeUnit.MINUTES);
                    return voList;
                });
    }
}
