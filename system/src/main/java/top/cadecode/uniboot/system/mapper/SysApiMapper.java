package top.cadecode.uniboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.cadecode.uniboot.system.bean.po.SysApi;
import top.cadecode.uniboot.system.bean.vo.SysApiVo.SysApiRolesVo;
import top.cadecode.uniboot.system.request.SysApiRequest.SysApiRolesRequest;

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
