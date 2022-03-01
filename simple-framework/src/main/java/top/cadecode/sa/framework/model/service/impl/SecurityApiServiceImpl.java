package top.cadecode.sa.framework.model.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.cadecode.sa.framework.model.mapper.SecurityApiMapper;
import top.cadecode.sa.framework.model.service.SecurityApiService;
import top.cadecode.sa.framework.model.vo.SecurityApiVo;

import java.util.List;
import java.util.Optional;

/**
 * @author Cade Li
 * @date 2022/1/5
 * @description spring security api service 实现
 */
@RequiredArgsConstructor
@Service
public class SecurityApiServiceImpl implements SecurityApiService {

    private final SecurityApiMapper securityApiMapper;
    private final RedisTemplate<String, List<SecurityApiVo>> redisTemplate;

    @Cacheable(cacheNames = API_ROLE_CACHE_PREFIX, cacheManager = "caffeineCache")
    @Override
    public List<SecurityApiVo> listSecurityApiVos() {
        List<SecurityApiVo> securityApiVos = redisTemplate.opsForValue().get(API_ROLE_CACHE_PREFIX);
        return Optional.ofNullable(securityApiVos)
                .orElseGet(() -> {
                    List<SecurityApiVo> vos = securityApiMapper.listSecurityApiVos();
                    redisTemplate.opsForValue().set(API_ROLE_CACHE_PREFIX, vos);
                    return vos;
                });
    }
}
