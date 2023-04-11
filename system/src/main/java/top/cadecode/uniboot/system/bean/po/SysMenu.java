package top.cadecode.uniboot.system.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 系统菜单 PO
 *
 * @author Cade Li
 * @since 2023/4/11
 */
@Data
public class SysMenu {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long parentId;

    private String routeName;

    private String routePath;

    private String menuName;

    private Boolean leafFlag;

    private String icon;

    private Integer order;

    private Boolean enableFlag;

    private Date createTime;

    private Date updateTime;

    private String updateUser;


}
