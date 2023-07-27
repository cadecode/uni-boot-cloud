package com.github.cadecode.uniboot.framework.svc.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.github.cadecode.uniboot.framework.api.config.UniBootConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 文件上传及下载工具类
 *
 * @author Cade Li
 * @since 2023/4/10
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class FileUploadUtil implements InitializingBean {

    private final UniBootConfig uniBootConfig;

    public static final String DEFAULT_FILE_BASE_PATH = "/uniboot/file/temp/";
    public static final String DEFAULT_DOWNLOAD_API = "/common/download";
    public static final String DEFAULT_DOWNLOAD_TEMP_API = "/common/download_temp";
    private static String UPLOAD_PATH;
    private static String DOWNLOAD_PATH;
    private static final Set<String> ALLOWED_EXTENSIONS = CollUtil.newHashSet(
            // image
            "bmp", "gif", "jpg", "jpeg", "png",
            // text
            "txt", "py", "java", "js",
            // office
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt", "pdf",
            // zip
            "rar", "zip", "gz", "bz2",
            // video
            "mp3", "mp4", "avi", "rmvb"
    );

    public static String uploadPath() {
        return UPLOAD_PATH;
    }

    public static String downloadPath() {
        return DOWNLOAD_PATH;
    }

    public static String downloadUrl(String fileName) {
        return DEFAULT_DOWNLOAD_API + "?fileName=" + fileName;
    }

    public static String downloadTempUrl(String fileName) {
        return DEFAULT_DOWNLOAD_TEMP_API + "?fileName=" + fileName;
    }

    public static boolean checkAllowedExtension(String fileName) {
        String suffix = FileNameUtil.getSuffix(fileName);
        return ALLOWED_EXTENSIONS.contains(suffix);
    }

    public static String renameByUUID(String fileName) {
        return UUID.fastUUID().toString(true) + "_" + fileName;
    }

    public static String renameByTime(String fileName) {
        return System.currentTimeMillis() + "_" + fileName;
    }

    @Override
    public void afterPropertiesSet() {
        if (StrUtil.isEmpty(uniBootConfig.getFileBasePath())) {
            uniBootConfig.setFileBasePath(DEFAULT_FILE_BASE_PATH);
            log.info("Set uniboot config file base path to default {}", DEFAULT_FILE_BASE_PATH);
        }
        UPLOAD_PATH = uniBootConfig.getFileBasePath() + "upload/";
        DOWNLOAD_PATH = uniBootConfig.getFileBasePath() + "download/";
        // 加入额外配置的后缀
        if (CollUtil.isNotEmpty(uniBootConfig.getAllowedFileExtensions())) {
            ALLOWED_EXTENSIONS.addAll(uniBootConfig.getAllowedFileExtensions());
        }
    }
}
