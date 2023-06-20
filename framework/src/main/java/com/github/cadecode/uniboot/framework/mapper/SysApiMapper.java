package com.github.cadecode.uniboot.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.cadecode.uniboot.framework.bean.po.SysApi;
import com.github.cadecode.uniboot.framework.bean.vo.SysApiVo.SysApiRolesVo;
import com.github.cadecode.uniboot.framework.request.SysApiRequest.SysApiRolesRequest;
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

    List<SysApiRolesVo> selectRolesVoByApiIds(@Param("apiIds") List<Long> apiIds);

    List<SysApiRolesVo> selectRolesVo(@Param("request") SysApiRolesRequest request);
}
