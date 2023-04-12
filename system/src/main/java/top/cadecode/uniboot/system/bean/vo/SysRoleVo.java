package top.cadecode.uniboot.system.bean.vo;

import lombok.Data;

/**
 * 系统角色 VO
 *
 * @author Cade Li
 * @date 2022/5/24
 */
public class SysRoleVo {

    @Data
    public static class SysRoleListVo {

        private Long id;

        private String code;

        private String name;

        private String description;
    }
}
