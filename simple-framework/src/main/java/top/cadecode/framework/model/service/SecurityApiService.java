package top.cadecode.framework.model.service;

import top.cadecode.framework.model.vo.SecurityApiVo;

import java.util.List;

/**
 * @author Cade Li
 * @date 2022/1/5
 * @description spring security api service 接口
 */
public interface SecurityApiService {

    /**
     * 查询所有接口及其角色
     *
     * @return api 列表
     */
    List<SecurityApiVo> listSecurityApiVos();
}
