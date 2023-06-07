package top.cadecode.uniboot.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import top.cadecode.uniboot.framework.bean.po.SysApi;
import top.cadecode.uniboot.framework.bean.vo.SysApiVo.SysApiRolesVo;
import top.cadecode.uniboot.framework.request.SysApiRequest.SysApiRolesRequest;

import java.util.List;

/**
 * 系统接口服务
 *
 * @author Cade Li
 * @date 2022/5/27
 */
public interface SysApiService extends IService<SysApi> {

    List<SysApiRolesVo> listRolesVo();

    List<SysApiRolesVo> listRolesVoByApiIds(List<Long> userIds);

    List<SysApiRolesVo> listRolesVo(SysApiRolesRequest request);

    PageInfo<SysApiRolesVo> pageRolesVo(SysApiRolesRequest request);
}
