package top.cadecode.sa.framework.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.cadecode.sa.framework.model.vo.SecurityUserVo;

/**
 * @author Cade Li
 * @date 2021/12/10
 * @description 用户信息 DAO
 */
@Mapper
public interface SecurityUserMapper {

    /**
     * 根据账户密码查询用户信息
     *
     * @param username 账户名
     * @return 用户信息 VO
     */
    SecurityUserVo getSecurityUserVo(String username);
}
