package com.github.cadecode.uniboot.framework.base.plugin.bean.vo;

import cn.hutool.core.lang.Dict;
import com.github.cadecode.uniboot.common.core.web.response.PageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Map;

/**
 * 文件记录 VO
 *
 * @author Cade Li
 * @since 2023/10/25
 */
public class PlgFileVo {

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class PlgFilePageReqVo extends PageParams {
        private Date startTime;
        private Date endTime;
        private String platform;
        private String url;
        private String filename;
        private String objectId;
        private String objectType;
    }

    @Data
    public static class PlgFilePageResVo {
        private Long id;
        private String url;
        private Long size;
        private String filename;
        private String originalFilename;
        private String basePath;
        private String path;
        private String ext;
        private String contentType;
        private String platform;
        private String thUrl;
        private String thFilename;
        private Long thSize;
        private String thContentType;
        private String objectId;
        private String objectType;
        private Map<String, String> metadata;
        private Map<String, String> userMetadata;
        private Map<String, String> thMetadata;
        private Map<String, String> thUserMetadata;
        private Dict attr;
        private Date createTime;
        private Date updateTime;
        private String updateUser;
    }
}
