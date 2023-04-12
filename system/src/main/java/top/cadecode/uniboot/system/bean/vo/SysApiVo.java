package top.cadecode.uniboot.system.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * 系统接口 VO
 *
 * @author Cade Li
 * @date 2022/5/24
 */
public class SysApiVo {

    @Data
    public static class SysApiRolesVo {

        private Long id;

        private String url;

        private String description;

        private List<String> roles;
    }
}
