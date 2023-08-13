package com.github.cadecode.uniboot.framework.svc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysApi;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiRolesReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiRolesResVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统接口 DAO
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysApiMapper extends BaseMapper<SysApi> {

    List<SysApiRolesResVo> selectRolesVoByApiIds(@Param("apiIds") List<Long> apiIds);

    List<SysApiRolesResVo> selectRolesVo(@Param("req") SysApiRolesReqVo reqVo);
}
