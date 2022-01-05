package top.cadecode.framework.model.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.cadecode.framework.model.mapper.SecurityApiMapper;
import top.cadecode.framework.model.service.SecurityApiService;
import top.cadecode.framework.model.vo.SecurityApiVo;

import java.util.List;

/**
 * @author Cade Li
 * @date 2022/1/5
 * @description spring security api service 实现
 */
@RequiredArgsConstructor
@Service
public class SecurityApiServiceImpl implements SecurityApiService {

    private final SecurityApiMapper securityApiMapper;

    @Cacheable(cacheNames = "apiRoleCache", cacheManager = "securityCacheManager")
    @Override
    public List<SecurityApiVo> listSecurityApiVos() {
        return securityApiMapper.listSecurityApiVos();
    }
}
