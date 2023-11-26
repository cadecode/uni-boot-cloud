package com.github.cadecode.uniboot.framework.svc.serviceimpl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysDept;
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
    public List<SysDeptTreeResVo> listTreeVo() {
        List<SysDept> poList = list().stream()
                .sorted(Comparator.comparing(SysDept::getOrderNum))
                .collect(Collectors.toList());
        List<SysDeptTreeResVo> treeResVoList = SysDeptConvert.INSTANCE.poToTreeResVo(poList);
        return geneTreeVo(treeResVoList, null);
    }

    private List<SysDeptTreeResVo> geneTreeVo(List<SysDeptTreeResVo> deptList, Long rootId) {
        List<SysDeptTreeResVo> parentList = deptList.stream().filter(o -> ObjUtil.equals(rootId, o.getParentId())).collect(Collectors.toList());
        parentList.forEach(p -> {
            List<SysDeptTreeResVo> children = deptList.stream()
                    .filter(c -> ObjUtil.equals(c.getParentId(), p.getId()))
                    .peek(c -> c.setChildren(geneTreeVo(deptList, c.getId())))
                    .collect(Collectors.toList());
            p.setChildren(children);
        });
        return parentList;
    }
}
