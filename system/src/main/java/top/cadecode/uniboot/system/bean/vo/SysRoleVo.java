package top.cadecode.uniboot.system.bean.vo;

import lombok.Data;

/**
 * 系统角色 VO
 *
 * @author Cade Li
 * @date 2022/5/24
 */
@Data
public class SysRoleVo {

    private Long id;

    private String code;

    private String name;

    private String description;
}
