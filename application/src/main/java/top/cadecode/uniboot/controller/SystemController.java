package top.cadecode.uniboot.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cadecode.uniboot.common.annotation.ApiFormat;
import top.cadecode.uniboot.common.exception.UniException;
import top.cadecode.uniboot.common.response.PageResult;
import top.cadecode.uniboot.framework.security.TokenAuthHolder;
import top.cadecode.uniboot.system.bean.dto.SysUserDto.SysUserDetailsDto;
import top.cadecode.uniboot.system.bean.dto.SysUserDto.SysUserInfoDto;
import top.cadecode.uniboot.system.bean.po.SysRole;
import top.cadecode.uniboot.system.bean.po.SysUser;
import top.cadecode.uniboot.system.bean.vo.SysMenuVo.SysMenuTreeVo;
import top.cadecode.uniboot.system.bean.vo.SysRoleVo.SysRoleListVo;
import top.cadecode.uniboot.system.bean.vo.SysUserVo.SysUserRolesVo;
import top.cadecode.uniboot.system.convert.SysRoleConvert;
import top.cadecode.uniboot.system.convert.SysUserConvert;
import top.cadecode.uniboot.system.service.SysApiService;
import top.cadecode.uniboot.system.service.SysMenuService;
import top.cadecode.uniboot.system.service.SysRoleService;
import top.cadecode.uniboot.system.service.SysUserService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static top.cadecode.uniboot.system.request.SysUserRequest.*;

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
@Validated
public class SystemController {

    private final SysUserService sysUserService;
    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;
    private final SysApiService sysApiService;

    private final PasswordEncoder passwordEncoder;

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

    @ApiOperation("修改用户信息（用户中心）")
    @PostMapping("user/modify_info")
    public boolean userModifyInfo(@RequestBody @Valid SysUserModifyInfoRequest request) {
        SysUserDetailsDto userDetails = TokenAuthHolder.getUserDetails(null);
        return sysUserService.lambdaUpdate()
                .eq(SysUser::getId, userDetails.getId())
                .set(SysUser::getNickName, request.getNickName())
                .set(SysUser::getPhone, request.getPhone())
                .set(SysUser::getMail, request.getMail())
                .set(SysUser::getSex, request.getSex())
                .update(new SysUser());
    }

    @ApiOperation("修改用户密码（用户中心）")
    @PostMapping("user/modify_pass")
    public boolean userModifyPass(@RequestBody @Valid SysUserModifyPassRequest request) {
        SysUserDetailsDto userDetails = TokenAuthHolder.getUserDetails(null);
        SysUser sysUser = sysUserService.lambdaQuery().select(SysUser::getPassword)
                .eq(SysUser::getId, userDetails.getId()).one();
        if (ObjectUtil.notEqual(request.getNewPass(), request.getConfirmedPass())) {
            throw UniException.of("新密码和确认密码不一致");
        }
        if (!passwordEncoder.matches(request.getOldPass(), sysUser.getPassword())) {
            throw UniException.of("原密码错误");
        }
        return sysUserService.lambdaUpdate()
                .eq(SysUser::getId, userDetails.getId())
                .eq(SysUser::getPassword, sysUser.getPassword())
                .set(SysUser::getPassword, passwordEncoder.encode(request.getNewPass()))
                .update(new SysUser());
    }

    @ApiOperation("查询用户列表（带角色）")
    @PostMapping("user/page_roles_vo")
    public PageResult<SysUserRolesVo> userPageRolesVo(@RequestBody @Valid SysUserRolesRequest request) {
        PageInfo<SysUserRolesVo> rolesVoPage = sysUserService.pageRolesVo(request);
        return new PageResult<>((int) rolesVoPage.getTotal(), rolesVoPage.getList());
    }

    @ApiOperation("更新用户启用状态")
    @PostMapping("user/update_enable")
    public boolean userUpdateEnable(@RequestBody @Valid SysUserUpdateEnableRequest request) {
        return sysUserService.lambdaUpdate()
                .eq(SysUser::getId, request.getId())
                .set(SysUser::getEnableFlag, request.getEnableFlag())
                .update(new SysUser());
    }

    @ApiOperation("更新用户")
    @PostMapping("user/update")
    public boolean userUpdate(@RequestBody @Valid SysUserUpdateRequest request) {
        String encodePass = null;
        if (ObjectUtil.isNotEmpty(request.getPassword())) {
            encodePass = passwordEncoder.encode(request.getPassword());
        }
        return sysUserService.lambdaUpdate()
                .eq(SysUser::getId, request.getId())
                .set(SysUser::getUsername, request.getUsername())
                .set(SysUser::getNickName, request.getNickName())
                .set(ObjectUtil.isNotEmpty(encodePass), SysUser::getPassword, encodePass)
                .set(SysUser::getPhone, request.getPhone())
                .set(SysUser::getMail, request.getMail())
                .set(SysUser::getSex, request.getSex())
                .update(new SysUser());
    }

    @ApiOperation("添加用户")
    @PostMapping("user/add")
    public boolean userAdd(@RequestBody @Valid SysUserAddRequest request) {
        if (ObjectUtil.isNotEmpty(request.getPassword())) {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        SysUser sysUser = SysUserConvert.INSTANCE.requestToPo(request);
        return sysUserService.save(sysUser);
    }

    @ApiOperation("删除用户（多选）")
    @PostMapping("user/delete")
    public boolean userDelete(@RequestBody @NotEmpty List<Long> userIdList) {
        return sysUserService.removeBatchByIds(userIdList);
    }

    @ApiOperation("获取用户（带角色）byUserIds")
    @PostMapping("user/list_roles_vo_by_user_ids")
    public List<SysUserRolesVo> userListRolesVoByUserIds(@RequestBody @NotEmpty List<Long> userIdList) {
        return sysUserService.listRolesVoByUserIds(userIdList);
    }

    @ApiOperation("查询角色列表")
    @PostMapping("role/list")
    public List<SysRoleListVo> roleList() {
        List<SysRole> roleList = sysRoleService.list();
        return SysRoleConvert.INSTANCE.poToListVo(roleList);
    }
}
