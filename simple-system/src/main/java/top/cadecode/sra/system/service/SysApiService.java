package top.cadecode.sra.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.cadecode.sra.system.bean.po.SysApi;
import top.cadecode.sra.system.bean.vo.SysApiVo;

import java.util.List;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统接口服务
 */
public interface SysApiService extends IService<SysApi> {

    List<SysApiVo> listSysApiVo();
}
