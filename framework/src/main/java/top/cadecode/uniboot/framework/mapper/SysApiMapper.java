package top.cadecode.uniboot.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.cadecode.uniboot.framework.bean.po.SysApi;
import top.cadecode.uniboot.framework.bean.vo.SysApiVo.SysApiRolesVo;
import top.cadecode.uniboot.framework.request.SysApiRequest.SysApiRolesRequest;

import java.util.List;

/**
 * 系统接口 DAO
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysApiMapper extends BaseMapper<SysApi> {

    List<SysApiRolesVo> selectRolesVoByApiIds(@Param("apiIds") List<Long> apiIds);

    List<SysApiRolesVo> selectRolesVo(@Param("request") SysApiRolesRequest request);
}
