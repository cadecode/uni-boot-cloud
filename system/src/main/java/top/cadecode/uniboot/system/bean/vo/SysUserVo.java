package top.cadecode.uniboot.system.bean.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 系统用户 VO
 *
 * @author Cade Li
 * @date 2022/5/24
 */
public class SysUserVo {

    @Data
    public static class SysUserRolesVo {

        private Long id;

        private String username;

        private String nickName;

        private boolean enableFlag;

        private String sex;

        private String mail;

        private String phone;

        private String loginIp;

        private Date loginDate;

        private List<String> roles;
    }
}
