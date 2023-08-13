package com.github.cadecode.uniboot.framework.api.consts;

/**
 * Api log 类型
 *
 * @author Cade Li
 * @since 2023/8/13
 */
public interface LogType {

    /**
     * curd
     */
    String QUERY = "Query";
    String UPDATE = "Update";
    String REMOVE = "Remove";
    String ADD = "Add";
    /**
     * 鉴权
     */
    String AUTH = "Auth";
    /**
     * 导入导出
     */
    String IMPORT = "Import";
    String EXPORT = "Export";
    /**
     * 上传下载
     */
    String UPLOAD = "Upload";
    String DOWNLOAD = "Download";
}
