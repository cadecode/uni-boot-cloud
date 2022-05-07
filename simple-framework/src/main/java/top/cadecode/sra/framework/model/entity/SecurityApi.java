package top.cadecode.sra.framework.model.entity;

import lombok.Data;

/**
 * @author Cade Li
 * @date 2021/12/5
 * @description Spring Security 接口类
 */
@Data
public class SecurityApi {
    // id
    private Long id;
    // 角色
    private String url;
    // 描述
    private String description;
}
