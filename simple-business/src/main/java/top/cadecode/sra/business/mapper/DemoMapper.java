package top.cadecode.sra.business.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.cadecode.sra.common.annotation.DataSource;

import java.util.List;

/**
 * @author Cade Li
 * @date 2021/12/3
 * @description ToDo
 */
@Mapper
public interface DemoMapper {

    @DataSource("db1")
    List<String> getOne();

    @DataSource("db2")
    List<String> getTwo();
}
