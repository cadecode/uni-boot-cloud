package top.cadecode.uniboot.system.serviceimpl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.cadecode.uniboot.system.bean.po.SysMenu;
import top.cadecode.uniboot.system.bean.vo.SysMenuVo.SysMenuTreeVo;
import top.cadecode.uniboot.system.convert.SysMenuConvert;
import top.cadecode.uniboot.system.mapper.SysMenuMapper;
import top.cadecode.uniboot.system.service.SysMenuService;

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
    public List<SysMenuTreeVo> listTreeVoByRoles(List<String> roleCodes) {
        List<SysMenu> sysMenus = listByRoles(roleCodes);
        List<SysMenuTreeVo> menuTreeVoList = sysMenus.stream()
                .filter(m -> ObjectUtil.equal(m.getEnableFlag(), true))
                .map(SysMenuConvert.INSTANCE::toTreeVo)
                .collect(Collectors.toList());
        return generateMenuTree(menuTreeVoList, null);
    }

    private List<SysMenuTreeVo> generateMenuTree(List<SysMenuTreeVo> menus, Long rootId) {
        List<SysMenuTreeVo> resultList = new ArrayList<>();
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
                List<SysMenuTreeVo> children = menu.getChildren();
                // 存儿子
                m.setChildren(generateMenuTree(menus, m.getId()));
                children.add(m);
            });
            resultList.add(menu);
        });
        return resultList;
    }

}
