package com.github.cadecode.uniboot.framework.svc.serviceimpl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cadecode.uniboot.common.core.util.TreeUtil;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysDept;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysDeptVo.SysDeptTreeReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysDeptVo.SysDeptTreeResVo;
import com.github.cadecode.uniboot.framework.svc.convert.SysDeptConvert;
import com.github.cadecode.uniboot.framework.svc.mapper.SysDeptMapper;
import com.github.cadecode.uniboot.framework.svc.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门服务实现
 *
 * @author Cade Li
 * @since 2023/11/24
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Override
    public List<SysDeptTreeResVo> listTreeVo(SysDeptTreeReqVo reqVo) {
        List<SysDept> poList = lambdaQuery()
                .likeRight(ObjUtil.isNotEmpty(reqVo.getDeptName()), SysDept::getDeptName, reqVo.getDeptName())
                .list()
                .stream()
                .sorted(Comparator.comparing(SysDept::getOrderNum))
                .collect(Collectors.toList());
        List<SysDeptTreeResVo> treeResVoList = SysDeptConvert.INSTANCE.poToTreeResVo(poList);
        return TreeUtil.listToTree(treeResVoList, null, SysDeptTreeResVo::getId, SysDeptTreeResVo::getParentId, SysDeptTreeResVo::setChildren);
    }
}
