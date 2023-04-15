package top.cadecode.uniboot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.cadecode.uniboot.system.bean.po.SysApi;
import top.cadecode.uniboot.system.bean.vo.SysApiVo.SysApiRolesVo;

import java.util.List;

/**
 * 系统接口服务
 *
 * @author Cade Li
 * @date 2022/5/27
 */
public interface SysApiService extends IService<SysApi> {

    List<SysApiRolesVo> listRolesVo();
}
