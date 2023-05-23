package top.cadecode.uniboot.controller.system;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cadecode.uniboot.common.annotation.ApiFormat;
import top.cadecode.uniboot.common.response.PageResult;
import top.cadecode.uniboot.system.bean.po.SysApi;
import top.cadecode.uniboot.system.bean.vo.SysApiVo.SysApiRolesVo;
import top.cadecode.uniboot.system.convert.SysApiConvert;
import top.cadecode.uniboot.system.service.SysApiService;
import top.cadecode.uniboot.system.service.SysRoleService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static top.cadecode.uniboot.system.request.SysApiRequest.*;

/**
 * API 管理
 *
 * @author Cade Li
 * @date 2023/5/14
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "API 管理")
@RequestMapping("system/api")
@RestController
@Validated
public class SysApiController {

    private final SysApiService sysApiService;
    private final SysRoleService sysRoleService;

    @ApiOperation("查询API列表（带角色）")
    @PostMapping("page_roles_vo")
    public PageResult<SysApiRolesVo> pageRolesVo(@RequestBody @Valid SysApiRolesRequest request) {
        PageInfo<SysApiRolesVo> rolesVoPage = sysApiService.pageRolesVo(request);
        return new PageResult<>((int) rolesVoPage.getTotal(), rolesVoPage.getList());
    }

    @ApiOperation("更新API")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid SysApiUpdateRequest request) {
        SysApi po = SysApiConvert.INSTANCE.requestToPo(request);
        return sysApiService.updateById(po);
    }

    @ApiOperation("添加API")
    @PostMapping("add")
    public boolean add(@RequestBody @Valid SysApiAddRequest request) {
        SysApi sysApi = SysApiConvert.INSTANCE.requestToPo(request);
        return sysApiService.save(sysApi);
    }

    @ApiOperation("删除API（多选）")
    @PostMapping("delete")
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(@RequestBody @NotEmpty List<Long> apiIdList) {
        // 清理API角色绑定关系
        sysRoleService.removeRoleApiByApiIds(apiIdList);
        return sysApiService.removeBatchByIds(apiIdList);
    }

    @ApiOperation("获取API（带角色）byApiIds")
    @PostMapping("list_roles_vo_by_api_ids")
    public List<SysApiRolesVo> listRolesVoByApiIds(@RequestBody @NotEmpty List<Long> apiIdList) {
        return sysApiService.listRolesVoByApiIds(apiIdList);
    }

}
