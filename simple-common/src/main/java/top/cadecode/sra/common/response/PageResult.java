package top.cadecode.sra.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 分页查询返回格式
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    /**
     * 总条数
     */
    private Integer total;

    /**
     * 当前结果集
     */
    private List<T> records;
}
