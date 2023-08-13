package com.github.cadecode.uniboot.framework.svc.serviceimpl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysMenu;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysMenuVo.SysMenuRolesReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysMenuVo.SysMenuRolesResVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysMenuVo.SysMenuTreeResVo;
import com.github.cadecode.uniboot.framework.svc.convert.SysMenuConvert;
import com.github.cadecode.uniboot.framework.svc.mapper.SysMenuMapper;
import com.github.cadecode.uniboot.framework.svc.service.SysMenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统菜单服务实现
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> listByRoles(List<String> roleCodes) {
        return sysMenuMapper.selectByRoles(roleCodes);
    }

    @Override
    public List<SysMenuTreeResVo> listTreeVoByRoles(List<String> roleCodes) {
        List<SysMenu> sysMenus = listByRoles(roleCodes);
        List<SysMenuTreeResVo> menuTreeVoList = sysMenus.stream()
                .filter(m -> ObjectUtil.equal(m.getEnableFlag(), true))
                .map(SysMenuConvert.INSTANCE::poToTreeResVo)
                .collect(Collectors.toList());
        return generateMenuTree(menuTreeVoList, null);
    }

    @Override
    public List<SysMenuRolesResVo> listRolesVo(SysMenuRolesReqVo reqVo) {
        return sysMenuMapper.selectRolesVo(reqVo);
    }

    @Override
    public PageInfo<SysMenuRolesResVo> pageRolesVo(SysMenuRolesReqVo reqVo) {
        return PageHelper.startPage(reqVo.getPageNumber(), reqVo.getPageSize())
                .doSelectPageInfo(() -> listRolesVo(reqVo));
    }

    @Override
    public List<SysMenuRolesResVo> listRolesVoByMenuIds(List<Long> menuIds) {
        return sysMenuMapper.selectRolesVoByMenuIds(menuIds);
    }

    private List<SysMenuTreeResVo> generateMenuTree(List<SysMenuTreeResVo> menus, Long rootId) {
        List<SysMenuTreeResVo> resultList = new ArrayList<>();
        menus.forEach(menu -> {
            // 确定下父亲
            if (ObjectUtil.notEqual(menu.getParentId(), rootId)) {
                return;
            }
            menus.forEach(m -> {
                // 比一下是不是儿子
                if (ObjectUtil.notEqual(m.getParentId(), menu.getId())) {
                    return;
                }
                // 拿儿子列表
                List<SysMenuTreeResVo> children = menu.getChildren();
                // 存儿子
                m.setChildren(generateMenuTree(menus, m.getId()));
                children.add(m);
            });
            resultList.add(menu);
        });
        return resultList;
    }

}
