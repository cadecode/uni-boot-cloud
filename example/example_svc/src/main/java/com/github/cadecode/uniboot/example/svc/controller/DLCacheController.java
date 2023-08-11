package com.github.cadecode.uniboot.example.svc.controller;

import com.github.cadecode.uniboot.common.plugin.cache.consts.CacheConst;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 二级缓存测试接口
 *
 * @author Cade Li
 * @date 2023/6/17
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "二级缓存测试")
@RestController
@RequestMapping("demo/dl")
public class DLCacheController {

    @ApiOperation("测试 @Cacheable")
    @Cacheable(cacheNames = "test", key = "'dl'", cacheManager = CacheConst.DL)
    @PostMapping("test_cacheable")
    public String testCacheable() {
        log.info("testCacheable 执行");
        return "Cacheable";
    }

    @ApiOperation("测试 @Cacheable null 值")
    @Cacheable(cacheNames = "test", key = "'dl'", cacheManager = CacheConst.DL)
    @PostMapping("test_cacheable_null")
    public String testCacheableNull() {
        log.info("testCacheableNull 执行");
        return null;
    }

    @ApiOperation("测试 @CachePut")
    @CachePut(cacheNames = "test", key = "'dl'", cacheManager = CacheConst.DL)
    @PostMapping("test_put")
    public String testPut() {
        return "Put";
    }

    @ApiOperation("测试 @CachePut null 值")
    @CachePut(cacheNames = "test", key = "'dl'", cacheManager = CacheConst.DL)
    @PostMapping("test_put_null")
    public String testPutNull() {
        return null;
    }

    @ApiOperation("测试 @CacheEvict")
    @CacheEvict(cacheNames = "test", key = "'dl'", cacheManager = CacheConst.DL)
    @PostMapping("test_evict")
    public String testEvict() {
        return "Evict";
    }
}
