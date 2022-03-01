package top.cadecode.sa.framework.model.service;

import top.cadecode.sa.framework.model.vo.SecurityApiVo;

import java.util.List;

/**
 * @author Cade Li
 * @date 2022/1/5
 * @description spring security api service 接口
 */
public interface SecurityApiService {

    /**
     * api role 缓存前缀
     */
    String API_ROLE_CACHE_PREFIX = "security:api.role:list";


    /**
     * 查询所有接口及其角色
     *
     * @return api 列表
     */
    List<SecurityApiVo> listSecurityApiVos();
}
