package com.github.cadecode.uniboot.example.svc.controller;

import cn.hutool.extra.servlet.ServletUtil;
import com.github.cadecode.uniboot.common.core.exception.ApiException;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * X File Storage 测试 API
 *
 * @author Cade Li
 * @since 2023/10/24
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = " X File Storage 测试")
@RestController
@RequestMapping("demo/x_file_storage")
public class XFileStorageController {

    private final FileStorageService fileStorageService;

    @ApiOperation("上传")
    @PostMapping("upload")
    public FileInfo localUpload(@RequestPart MultipartFile file,
                                @RequestPart(required = false) String customFileName) {
        return fileStorageService.of(file)
                .setSaveFilename(customFileName)
                .upload();
    }

    @ApiOperation("上传图片（打开缩略）")
    @PostMapping("upload-image")
    public FileInfo uploadImage(@RequestPart MultipartFile file,
                                @RequestPart(required = false) String customFileName) {
        return fileStorageService.of(file)
                .setSaveFilename(customFileName)
                .image(img -> img.size(1000, 1000))
                .thumbnail(th -> th.size(200, 200))
                .upload();
    }

    @ApiOperation("下载")
    @GetMapping("download")
    public void download(HttpServletResponse response,
                         @RequestParam String url) {
        // 手动构造文件信息，可用于其它操作
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(url);
        // 文件是否存在
        boolean exists = fileStorageService.exists(fileInfo);
        if (!exists) {
            throw ApiException.of("文件不存在！");
        }
        // 下载
        fileStorageService.download(fileInfo).inputStream(in -> ServletUtil.write(response, in));
    }
}
