package top.cadecode.sra.framework.model.entity;

import lombok.Data;

/**
 * @author Cade Li
 * @date 2021/12/5
 * @description Spring Security 角色类
 */
@Data
public class SecurityRole {
    // id
    private Long id;
    // 角色
    private String code;
    // 名称
    private String name;
    // 描述
    private String description;
}
