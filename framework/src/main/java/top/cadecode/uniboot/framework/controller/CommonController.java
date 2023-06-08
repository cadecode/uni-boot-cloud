package top.cadecode.uniboot.framework.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.servlet.ServletUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.cadecode.uniboot.common.core.exception.UniException;
import top.cadecode.uniboot.common.core.util.AssertUtil;
import top.cadecode.uniboot.framework.annotation.ApiFormat;
import top.cadecode.uniboot.framework.enums.FrameErrorEnum;
import top.cadecode.uniboot.framework.util.FileUploadUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 通用API
 *
 * @author Cade Li
 * @since 2023/4/9
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "通用接口")
@RestController
public class CommonController {

    @ApiOperation("上传文件")
    @PostMapping("common/upload")
    public boolean upload(@RequestPart("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        boolean checked = FileUploadUtil.checkAllowedExtension(originalFilename);
        AssertUtil.isFalse(checked, FrameErrorEnum.EXTENSION_NOT_ALLOWED, originalFilename + "不被允许");
        String renamedFileName = FileUploadUtil.renameByUUID(originalFilename);
        String targetFilePath = FileUploadUtil.uploadPath() + renamedFileName;
        try {
            File targetFile = FileUtil.file(targetFilePath);
            FileUtil.mkParentDirs(targetFile);
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw UniException.of(FrameErrorEnum.UPLOAD_FILE_FAIL, e, originalFilename + "上传失败");
        }
        return true;
    }

    @ApiOperation("上传多文件")
    @PostMapping("common/upload_files")
    public int uploadFiles(@RequestPart("files") MultipartFile[] files) {
        int res = 0;
        for (MultipartFile file : files) {
            upload(file);
            res++;
        }
        return res;
    }

    @ApiOperation("下载文件")
    @GetMapping(FileUploadUtil.DEFAULT_DOWNLOAD_API)
    public void download(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException {
        boolean checked = FileUploadUtil.checkAllowedExtension(fileName);
        AssertUtil.isFalse(checked, FrameErrorEnum.EXTENSION_NOT_ALLOWED, fileName + "不被允许");
        // 写文件流
        String targetFilePath = FileUploadUtil.downloadPath() + fileName;
        boolean exist = FileUtil.exist(targetFilePath);
        AssertUtil.isFalse(exist, FrameErrorEnum.FILE_NOT_FOUND, fileName + "不存在");
        ServletUtil.write(response, new File(targetFilePath));
    }

    @ApiOperation("下载临时文件")
    @GetMapping(FileUploadUtil.DEFAULT_DOWNLOAD_TEMP_API)
    public void downloadTemp(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException {
        download(request, response, fileName);
        String targetFilePath = FileUploadUtil.downloadPath() + fileName;
        FileUtil.del(targetFilePath);
    }

}
