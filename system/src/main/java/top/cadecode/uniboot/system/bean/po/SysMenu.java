package top.cadecode.uniboot.system.bean.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.cadecode.uniboot.common.mybatis.handler.BoolToIntTypeHandler;

import java.util.Date;

/**
 * 系统菜单 PO
 *
 * @author Cade Li
 * @since 2023/4/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @TableField(typeHandler = BoolToIntTypeHandler.class)
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
    @TableField(typeHandler = BoolToIntTypeHandler.class)
    private Boolean enableFlag;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;


}
