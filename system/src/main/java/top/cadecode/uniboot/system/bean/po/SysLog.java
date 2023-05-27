package top.cadecode.uniboot.system.bean.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.cadecode.uniboot.common.enums.LogTypeEnum;
import top.cadecode.uniboot.common.mybatis.handler.BoolToIntTypeHandler;

import java.util.Date;

/**
 * 系统日志
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(autoResultMap = true)
public class SysLog {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private LogTypeEnum logType;
    /**
     * url
     */
    private String url;
    /**
     * 是否异常
     */
    @TableField(typeHandler = BoolToIntTypeHandler.class)
    private Boolean exceptional;
    /**
     * 访问者
     */
    private String accessUser;
    /**
     * 描述
     */
    private String description;
    /**
     * 类方法
     */
    private String classMethod;
    /**
     * 线程id
     */
    private String threadId;
    /**
     * 线程名称
     */
    private String threadName;
    /**
     * ip
     */
    private String ip;
    /**
     * http 方法
     */
    private String httpMethod;
    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 返回结果
     */
    private String result;
    /**
     * 接口耗时
     */
    private Long timeCost;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * user-agent
     */
    private String userAgent;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
}
