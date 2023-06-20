package com.github.cadecode.uniboot.common.plugin.log.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Api log 类型
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Getter
public enum LogTypeEnum {

    /**
     * curd
     */
    QUERY("Query"),
    UPDATE("Update"),
    REMOVE("Remove"),
    ADD("Add"),
    /**
     * 鉴权
     */
    AUTH("Auth"),
    /**
     * 导入导出
     */
    IMPORT("Import"),
    EXPORT("Export"),
    /**
     * 上传下载
     */
    UPLOAD("Upload"),
    DOWNLOAD("Download"),
    /**
     * 其他
     */
    OTHER("Other")
    ;

    @EnumValue
    @JsonValue
    private final String type;

    LogTypeEnum(String type) {
        this.type = type;
    }
}
