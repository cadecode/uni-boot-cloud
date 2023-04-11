package top.cadecode.uniboot.system.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.cadecode.uniboot.system.bean.po.SysMenu;
import top.cadecode.uniboot.system.mapper.SysMenuMapper;
import top.cadecode.uniboot.system.service.SysMenuService;

/**
 * 系统菜单服务实现
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
}
