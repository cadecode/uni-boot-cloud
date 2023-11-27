package com.github.cadecode.uniboot.framework.svc.bean.po;

import com.baomidou.mybatisplus.annotation.*;
import com.github.cadecode.uniboot.common.plugin.mybatis.converter.BoolToIntTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统用户 PO
 *
 * @author Cade Li
 * @date 2022/5/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(autoResultMap = true)
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
     * 部门 ID
     */
    private Long deptId;

    /**
     * 是否启用
     */
    @TableField(typeHandler = BoolToIntTypeHandler.class)
    private Boolean enableFlag;

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

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
}
