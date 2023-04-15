package top.cadecode.uniboot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cadecode.uniboot.common.annotation.ApiFormat;
import top.cadecode.uniboot.framework.security.TokenAuthHolder;
import top.cadecode.uniboot.system.bean.dto.SysUserDto.SysUserDetailsDto;
import top.cadecode.uniboot.system.bean.dto.SysUserDto.SysUserInfoDto;
import top.cadecode.uniboot.system.bean.vo.SysMenuVo.SysMenuTreeVo;
import top.cadecode.uniboot.system.service.SysMenuService;

import java.util.List;

/**
 * 系统管理API
 *
 * @author Cade Li
 * @since 2023/4/12
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "系统管理")
@RequestMapping("system")
@RestController
public class SystemController {

    private final SysMenuService sysMenuService;

    /**
     * 获取用户信息
     */
    @ApiOperation("获取用户信息")
    @PostMapping("user/get_info")
    public SysUserInfoDto userGetInfo() {
        SysUserDetailsDto userDetails = TokenAuthHolder.getUserDetails(null);
        List<SysMenuTreeVo> sysMenuTreeVos = sysMenuService.listTreeVoByRoles(userDetails.getRoles());
        return SysUserInfoDto.builder().menuList(sysMenuTreeVos).build();
    }
}
