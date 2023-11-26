package com.github.cadecode.uniboot.framework.svc.bean.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 部门 PO
 *
 * @author Cade Li
 * @since 2023/11/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(autoResultMap = true)
public class SysDept {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 父级 ID
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * leader 名
     */
    private String leader;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 电话
     */
    private String phone;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
}
