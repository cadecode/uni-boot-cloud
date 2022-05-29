package top.cadecode.sra.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.cadecode.sra.system.bean.po.SysApi;
import top.cadecode.sra.system.bean.vo.SysApiVo;

import java.util.List;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统接口 DAO
 */
@Mapper
public interface SysApiMapper extends BaseMapper<SysApi> {

    List<SysApiVo> listSysApiVo(@Param("apiIds") List<Long> apiIds);
}
