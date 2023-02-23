package top.cadecode.uniboot.system.bean.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * 系统用户 VO
 *
 * @author Cade Li
 * @date 2022/5/24
 */
@Data
public class SysUserVo {

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
     * 昵称
     */
    private String nickName;

    /**
     * 是否启用
     */
    private boolean enableFlag;

    /**
     * 角色
     */
    private List<String> roles;
}
