package com.github.cadecode.uniboot.framework.svc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysDept;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysDeptVo.SysDeptTreeResVo;

import java.util.List;

/**
 * 部门服务
 *
 * @author Cade Li
 * @since 2023/11/24
 */
public interface SysDeptService extends IService<SysDept> {

    List<SysDeptTreeResVo> listTreeVo();
}
