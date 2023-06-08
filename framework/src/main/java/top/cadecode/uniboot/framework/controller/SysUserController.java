package top.cadecode.uniboot.framework.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cadecode.uniboot.common.core.exception.UniException;
import top.cadecode.uniboot.common.core.web.response.PageResult;
import top.cadecode.uniboot.framework.annotation.ApiFormat;
import top.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserDetailsDto;
import top.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserInfoDto;
import top.cadecode.uniboot.framework.bean.po.SysUser;
import top.cadecode.uniboot.framework.bean.vo.SysMenuVo.SysMenuTreeVo;
import top.cadecode.uniboot.framework.bean.vo.SysUserVo.SysUserRolesVo;
import top.cadecode.uniboot.framework.convert.SysUserConvert;
import top.cadecode.uniboot.framework.service.SysMenuService;
import top.cadecode.uniboot.framework.service.SysRoleService;
import top.cadecode.uniboot.framework.service.SysUserService;
import top.cadecode.uniboot.framework.util.SecurityUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static top.cadecode.uniboot.framework.request.SysUserRequest.*;

/**
 * 用户管理API
 *
 * @author Cade Li
 * @since 2023/4/12
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "用户管理")
@RequestMapping("system/user")
@RestController
@Validated
public class SysUserController {

    private final SysUserService sysUserService;
    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;

    private final PasswordEncoder passwordEncoder;

    /**
     * 获取用户信息
     */
    @ApiOperation("获取用户信息")
    @PostMapping("get_info")
    public SysUserInfoDto getInfo() {
        SysUserDetailsDto userDetails = SecurityUtil.getUserDetails(null);
        List<SysMenuTreeVo> sysMenuTreeVos = sysMenuService.listTreeVoByRoles(userDetails.getRoles());
        return SysUserInfoDto.builder().menuList(sysMenuTreeVos).build();
    }

    @ApiOperation("修改用户信息（用户中心）")
    @PostMapping("modify_info")
    public boolean modifyInfo(@RequestBody @Valid SysUserModifyInfoRequest request) {
        SysUserDetailsDto userDetails = SecurityUtil.getUserDetails(null);
        SysUser po = SysUserConvert.INSTANCE.requestToPo(request);
        po.setId(userDetails.getId());
        return sysUserService.updateById(po);
    }

    @ApiOperation("修改用户密码（用户中心）")
    @PostMapping("modify_pass")
    public boolean modifyPass(@RequestBody @Valid SysUserModifyPassRequest request) {
        SysUserDetailsDto userDetails = SecurityUtil.getUserDetails(null);
        SysUser sysUser = sysUserService.lambdaQuery().select(SysUser::getPassword)
                .eq(SysUser::getId, userDetails.getId()).one();
        if (ObjectUtil.notEqual(request.getNewPass(), request.getConfirmedPass())) {
            throw UniException.of("新密码和确认密码不一致");
        }
        if (!passwordEncoder.matches(request.getOldPass(), sysUser.getPassword())) {
            throw UniException.of("原密码错误");
        }
        return sysUserService.lambdaUpdate()
                .setEntity(SysUser.builder()
                        .id(userDetails.getId())
                        .password(sysUser.getPassword())
                        .build())
                .update(SysUser.builder()
                        .password(passwordEncoder.encode(request.getNewPass()))
                        .build());
    }

    @ApiOperation("查询用户列表（带角色）")
    @PostMapping("page_roles_vo")
    public PageResult<SysUserRolesVo> pageRolesVo(@RequestBody @Valid SysUserRolesRequest request) {
        PageInfo<SysUserRolesVo> rolesVoPage = sysUserService.pageRolesVo(request);
        return new PageResult<>((int) rolesVoPage.getTotal(), rolesVoPage.getList());
    }

    @ApiOperation("更新用户启用状态")
    @PostMapping("update_enable")
    public boolean updateEnable(@RequestBody @Valid SysUserUpdateEnableRequest request) {
        return sysUserService.updateById(SysUser.builder()
                .id(request.getId())
                .enableFlag(request.getEnableFlag())
                .build());
    }

    @ApiOperation("更新用户")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid SysUserUpdateRequest request) {
        String encodePass = null;
        if (ObjectUtil.isNotEmpty(request.getPassword())) {
            encodePass = passwordEncoder.encode(request.getPassword());
        }
        SysUser po = SysUserConvert.INSTANCE.requestToPo(request);
        po.setPassword(encodePass);
        return sysUserService.updateById(po);
    }

    @ApiOperation("添加用户")
    @PostMapping("add")
    public boolean add(@RequestBody @Valid SysUserAddRequest request) {
        if (ObjectUtil.isNotEmpty(request.getPassword())) {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        SysUser sysUser = SysUserConvert.INSTANCE.requestToPo(request);
        return sysUserService.save(sysUser);
    }

    @ApiOperation("删除用户（多选）")
    @PostMapping("delete")
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(@RequestBody @NotEmpty List<Long> userIdList) {
        // 清理用户角色绑定关系
        sysRoleService.removeRoleUserByUserIds(userIdList);
        return sysUserService.removeBatchByIds(userIdList);
    }

    @ApiOperation("获取用户（带角色）byUserIds")
    @PostMapping("list_roles_vo_by_user_ids")
    public List<SysUserRolesVo> listRolesVoByUserIds(@RequestBody @NotEmpty List<Long> userIdList) {
        return sysUserService.listRolesVoByUserIds(userIdList);
    }

}
