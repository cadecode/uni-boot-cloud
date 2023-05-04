package top.cadecode.uniboot.controller.demo;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cadecode.uniboot.common.annotation.ApiFormat;

import java.sql.SQLException;

/**
 * dynamic-datasource 测试
 *
 * @author Cade Li
 * @date 2023/2/22
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "dynamic-datasource 测试")
@DependsOn("dataSource")
@RestController
@RequestMapping("demo/ds")
public class DynamicDsController {

    /**
     * 注入 Spring 数据源，用来查看 jdbc url 验证数据源切换
     */
    private final AbstractDataSource dataSource;

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

}
