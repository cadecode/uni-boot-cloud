package com.github.cadecode.uniboot.framework.base.plugin.bean.po;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.annotation.*;
import com.github.cadecode.uniboot.common.plugin.mybatis.converter.ObjToStrTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

/**
 * 文件记录
 *
 * @author Cade Li
 * @since 2023/10/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(autoResultMap = true)
public class PlgFile {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件访问地址
     */
    private String url;

    /**
     * 文件大小，单位字节
     */
    private Long size;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 原始文件名
     */
    private String originalFilename;

    /**
     * 基础存储路径
     */
    private String basePath;

    /**
     * 存储路径
     */
    private String path;

    /**
     * 文件扩展名
     */
    private String ext;

    /**
     * MIME 类型
     */
    private String contentType;

    /**
     * 存储平台
     */
    private String platform;

    /**
     * 缩略图访问路径
     */
    private String thUrl;

    /**
     * 缩略图名称
     */
    private String thFilename;

    /**
     * 缩略图大小，单位字节
     */
    private Long thSize;

    /**
     * 缩略图 MIME 类型
     */
    private String thContentType;

    /**
     * 文件所属对象id
     */
    private String objectId;

    /**
     * 文件所属对象类型，例如用户头像，评价图片
     */
    private String objectType;

    /**
     * 文件元数据
     */
    @TableField(typeHandler = ObjToStrTypeHandler.class)
    private Map<String, String> metadata;

    /**
     * 文件用户元数据
     */
    @TableField(typeHandler = ObjToStrTypeHandler.class)
    private Map<String, String> userMetadata;

    /**
     * 缩略图元数据
     */
    @TableField(typeHandler = ObjToStrTypeHandler.class)
    private Map<String, String> thMetadata;

    /**
     * 缩略图用户元数据
     */
    @TableField(typeHandler = ObjToStrTypeHandler.class)
    private Map<String, String> thUserMetadata;

    /**
     * 附加属性字典
     */
    @TableField(typeHandler = ObjToStrTypeHandler.class)
    private Dict attr;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
}
