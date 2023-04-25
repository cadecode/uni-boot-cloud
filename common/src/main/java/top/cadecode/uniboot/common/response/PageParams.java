package top.cadecode.uniboot.common.response;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 分页参数
 *
 * @author Cade Li
 * @date 2023/4/23
 */
@Data
public class PageParams {
    @NotNull
    private Integer pageSize = 1;
    @NotNull
    private Integer pageNumber = 10;

    private String orderBy;
}
