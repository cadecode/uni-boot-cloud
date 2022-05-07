package top.cadecode.sra.framework.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Cade Li
 * @date 2021/12/18
 * @description Spring Security api vo
 */
@Data
public class SecurityApiVo {
    private Long id;
    private String url;
    private String description;
    private List<String> roles;
}
