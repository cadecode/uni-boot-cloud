package com.github.cadecode.uniboot.framework.svc.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.cadecode.uniboot.common.core.web.response.PageResult;
import com.github.cadecode.uniboot.framework.api.consts.KeyPrefixConst;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.base.annotation.ApiInner;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysApi;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiRolesVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiSwaggerVo;
import com.github.cadecode.uniboot.framework.svc.convert.SysApiConvert;
import com.github.cadecode.uniboot.framework.svc.request.SysApiRequest;
import com.github.cadecode.uniboot.framework.svc.service.SysApiService;
import com.github.cadecode.uniboot.framework.svc.service.SysRoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * 获取全部接口的处理器 mapping
     */
    private final RequestMappingHandlerMapping handlerMapping;

    @ApiOperation("查询API列表（带角色）")
    @PostMapping("page_roles_vo")
    public PageResult<SysApiRolesVo> pageRolesVo(@RequestBody @Valid SysApiRequest.SysApiRolesRequest request) {
        PageInfo<SysApiRolesVo> rolesVoPage = sysApiService.pageRolesVo(request);
        return new PageResult<>((int) rolesVoPage.getTotal(), rolesVoPage.getList());
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'all'")
    @ApiOperation("更新API")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid SysApiRequest.SysApiUpdateRequest request) {
        SysApi po = SysApiConvert.INSTANCE.requestToPo(request);
        return sysApiService.updateById(po);
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'all'")
    @ApiOperation("添加API")
    @PostMapping("add")
    public boolean add(@RequestBody @Valid SysApiRequest.SysApiAddRequest request) {
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

    @ApiOperation("获取全部接口及 swagger 注解")
    @PostMapping("list_swagger_vo")
    public List<SysApiSwaggerVo> listSwaggerVo() {
        Map<RequestMappingInfo, HandlerMethod> methodMap = handlerMapping.getHandlerMethods();
        return methodMap.entrySet()
                .stream()
                .map(e -> {
                    ArrayList<String> urlList = new ArrayList<>(e.getKey().getPatternsCondition().getPatterns());
                    String url = null;
                    if (ObjectUtil.isNotEmpty(urlList)) {
                        url = urlList.get(0);
                    }
                    ApiOperation operation = e.getValue().getMethod().getAnnotation(ApiOperation.class);
                    String description = null;
                    if (ObjectUtil.isNotNull(operation)) {
                        description = operation.value();
                    }
                    return SysApiSwaggerVo.builder().url(url).description(description).build();
                })
                .filter(o -> ObjectUtil.isNotEmpty(o.getUrl()))
                .distinct()
                .sorted(Comparator.comparing(SysApiSwaggerVo::getUrl))
                .collect(Collectors.toList());
    }

    @ApiInner(onlyClient = true)
    @ApiOperation("查询API列表-全部")
    @PostMapping("list_roles_vo")
    public List<SysApiRolesVo> listRolesVo() {
        return sysApiService.listRolesVo();
    }
}
