package top.cadecode.framework.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.cadecode.framework.model.vo.SecurityUserVo;

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

    /**
     * 设置用户刷新 Token
     *
     * @param id    用户 ID
     * @param token token
     * @return 行数
     */
    int updateSecurityUserToken(long id, String token);
}
