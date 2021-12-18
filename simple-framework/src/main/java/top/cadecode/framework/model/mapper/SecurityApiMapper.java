package top.cadecode.framework.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.cadecode.framework.model.vo.SecurityApiVo;

import java.util.List;

/**
 * @author Cade Li
 * @date 2021/12/10
 * @description 角色信息 DAO
 */
@Mapper
public interface SecurityApiMapper {

    /**
     * 查询所有接口及其角色
     *
     * @return url
     */
    List<SecurityApiVo> listSecurityApiVo();

}
