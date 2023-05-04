package top.cadecode.uniboot.controller.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cadecode.uniboot.common.annotation.ApiFormat;
import top.cadecode.uniboot.system.bean.po.SysRole;
import top.cadecode.uniboot.system.bean.vo.SysRoleVo.SysRoleListVo;
import top.cadecode.uniboot.system.convert.SysRoleConvert;
import top.cadecode.uniboot.system.service.SysRoleService;

import java.util.List;

/**
 * 角色管理API
 *
 * @author Cade Li
 * @since 2023/5/4
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "角色管理")
@RequestMapping("system/role")
@RestController
@Validated
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @ApiOperation("查询角色列表")
    @PostMapping("list")
    public List<SysRoleListVo> roleList() {
        List<SysRole> roleList = sysRoleService.list();
        return SysRoleConvert.INSTANCE.poToListVo(roleList);
    }
}
