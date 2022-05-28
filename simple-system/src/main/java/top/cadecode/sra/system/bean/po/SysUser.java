package top.cadecode.sra.system.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author Cade Li
 * @date 2022/5/24
 * @description 系统用户 PO
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

    private Date createTime;

    private Date updateTime;

    private String updateUser;
}
