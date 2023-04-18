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

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 路由路径
     */
    private String routePath;

    /**
     * 组件路径
     */
    private String componentPath;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 是否页面
     */
    private Boolean leafFlag;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 是否启用
     */
    private Boolean enableFlag;

    private Date createTime;

    private Date updateTime;

    private String updateUser;


}
