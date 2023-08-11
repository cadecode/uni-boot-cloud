package com.github.cadecode.uniboot.framework.svc.bean.po;

import com.baomidou.mybatisplus.annotation.*;
import com.github.cadecode.uniboot.common.plugin.mybatis.converter.BoolToIntTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统字典
 *
 * @author Cade Li
 * @date 2023/6/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(autoResultMap = true)
public class SysDict {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 是否是默认值
     */
    @TableField(typeHandler = BoolToIntTypeHandler.class)
    private Boolean defaultFlag;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer orderNum;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

}
