package top.cadecode.uniboot.system.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户 PO
 *
 * @author Cade Li
 * @date 2022/5/24
 */
@Data
public class SysUser {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 是否启用
     */
    private boolean enableFlag;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 电话
     */
    private String phone;

    /**
     * 登录 IP
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private Date loginDate;

    private Date createTime;

    private Date updateTime;

    private String updateUser;
}
