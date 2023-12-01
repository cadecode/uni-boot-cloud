package com.github.cadecode.uniboot.example.svc.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.cadecode.uniboot.common.plugin.datasource.config.ShardingJdbcConfig;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgLog;
import com.github.cadecode.uniboot.framework.base.plugin.service.PlgLogService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * dynamic-datasource 测试
 *
 * @author Cade Li
 * @date 2023/2/22
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "动态数据源测试")
@DependsOn("dataSource")
@RestController
@RequestMapping("demo/ds")
public class DynamicDsController {

    /**
     * 注入 Spring 数据源，用来查看 jdbc url 验证数据源切换
     */
    private final AbstractDataSource dataSource;

    private final PlgLogService plgLogService;

    @DS("master")
    @ApiOperation("测试 master 数据源")
    @PostMapping("test_master")
    public String testMaster() throws SQLException {
        return dataSource.getConnection().getMetaData().getURL();
    }

    @DS("slave1")
    @ApiOperation("测试 slave1 数据源")
    @PostMapping("test_slave1")
    public String testSlave1() throws SQLException {
        return dataSource.getConnection().getMetaData().getURL();
    }

    @DS(ShardingJdbcConfig.SHARDING_DATA_SOURCE_NAME)
    @ApiOperation("测试 sharding 数据源读取")
    @PostMapping("test_sharding_read")
    public List<PlgLog> testShardingRead() throws SQLException {
        return PageHelper.startPage(0, 10)
                .<PlgLog>doSelectPageInfo(plgLogService::list).getList();
    }

    @DS(ShardingJdbcConfig.SHARDING_DATA_SOURCE_NAME)
    @ApiOperation("测试 sharding 数据源写入")
    @PostMapping("test_sharding_write")
    public boolean testShardingWrite() throws SQLException {
        return plgLogService.save(PlgLog.builder()
                .logType("测试LOG")
                .exceptional(false)
                .classMethod("DynamicDsController.testShardingWriteAndRead")
                .build());
    }

}
