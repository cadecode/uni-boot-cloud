package top.cadecode.uniboot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import top.cadecode.uniboot.system.bean.po.SysApi;
import top.cadecode.uniboot.system.bean.vo.SysApiVo.SysApiRolesVo;
import top.cadecode.uniboot.system.request.SysApiRequest.SysApiRolesRequest;

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
