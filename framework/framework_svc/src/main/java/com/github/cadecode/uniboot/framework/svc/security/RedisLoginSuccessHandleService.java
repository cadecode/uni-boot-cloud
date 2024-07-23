package com.github.cadecode.uniboot.framework.svc.security;

import cn.hutool.core.util.ObjUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyContext;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.common.plugin.cache.util.KeyGeneUtil;
import com.github.cadecode.uniboot.common.plugin.cache.util.RedisUtil;
import com.github.cadecode.uniboot.framework.api.consts.HttpConst;
import com.github.cadecode.uniboot.framework.api.consts.KeyPrefixConst;
import com.github.cadecode.uniboot.framework.api.enums.AuthModelEnum;
import com.github.cadecode.uniboot.framework.base.security.model.SysUserDetails;
import com.github.cadecode.uniboot.framework.base.util.SecurityUtil;
import com.github.cadecode.uniboot.framework.svc.config.FrameSecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 登录成功处理器
 *
 * @author Cade Li
 * @date 2021/12/11
 */
@Component
public class RedisLoginSuccessHandleService extends LoginSuccessHandleService {

    @Override
    public ApiResult<SysUserDetails> getResult(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 从认证信息中获取用户对象
        SysUserDetails sysUserDetails = (SysUserDetails) authentication.getPrincipal();
        // 生成 uuid token
        String uuidToken = SecurityUtil.generateUUID();
        // token 放在请求头
        response.addHeader(HttpConst.HEAD_TOKEN, uuidToken);
        // 生成存放登录信息的 redis key
        String loginUserTokenKey = KeyGeneUtil.key(KeyPrefixConst.LOGIN_USER, uuidToken);
        String loginUsernameKey = KeyGeneUtil.key(KeyPrefixConst.LOGIN_USER, sysUserDetails.getUsername());
        // 获取当前账号 token 列表
        List<String> tokenList = getTokenList(loginUsernameKey, loginUserTokenKey);
        RedisUtil.set(loginUserTokenKey, sysUserDetails, SecurityUtil.getExpiration(), TimeUnit.SECONDS);
        RedisUtil.set(loginUsernameKey, tokenList, SecurityUtil.getExpiration(), TimeUnit.SECONDS);
        return ApiResult.ok(sysUserDetails).path(FrameSecurityConfig.LOGOUT_URL);
    }

    private List<String> getTokenList(String loginUsernameKey, String loginUserTokenKey) {
        List<String> tokenList = RedisUtil.get(loginUsernameKey, new TypeReference<ArrayList<String>>() {});
        if (ObjUtil.isEmpty(tokenList)) {
            return new ArrayList<>(Collections.singletonList(loginUserTokenKey));
        }
        // 当配置了最大 token 数
        Integer maxTokenCount = SecurityUtil.getMaxCount();
        if (Objects.nonNull(maxTokenCount) && maxTokenCount > 0) {
            tokenList.add(loginUserTokenKey);
            if (tokenList.size() > maxTokenCount) {
                // 清理超过限制的 token
                tokenList.stream()
                        .limit(tokenList.size() - maxTokenCount)
                        .forEach(RedisUtil::del);
                tokenList = tokenList.stream()
                        .skip(tokenList.size() - maxTokenCount)
                        .collect(Collectors.toList());
            }
        } else {
            tokenList = new ArrayList<>(Collections.singletonList(loginUserTokenKey));
        }
        return tokenList;
    }

    @Override
    public boolean supports(StrategyContext delimiter) {
        return delimiter.getStrategyType() == AuthModelEnum.REDIS;
    }
}
