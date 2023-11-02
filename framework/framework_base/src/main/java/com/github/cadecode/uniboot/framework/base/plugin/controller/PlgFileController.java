package com.github.cadecode.uniboot.framework.base.plugin.controller;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.github.cadecode.uniboot.common.core.web.response.PageResult;
import com.github.cadecode.uniboot.common.plugin.log.annotation.ApiLogger;
import com.github.cadecode.uniboot.framework.api.consts.LogTypeConst;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgFile;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgFileVo.PlgFilePageReqVo;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgFileVo.PlgFilePageResVo;
import com.github.cadecode.uniboot.framework.base.plugin.convert.PlgFileConvert;
import com.github.cadecode.uniboot.framework.base.plugin.service.PlgFileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.UploadPretreatment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件记录管理
 *
 * @author Cade Li
 * @since 2023/10/26
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "文件管理")
@RequestMapping("plugin/file")
@RestController
@Validated
public class PlgFileController {

    private final FileStorageService fileStorageService;

    private final PlgFileService plgFileService;

    @ApiOperation("查询列表")
    @PostMapping("record/page")
    public PageResult<PlgFilePageResVo> page(@RequestBody @Valid PlgFilePageReqVo reqVo) {
        PageInfo<PlgFile> pageInfo = PageHelper.startPage(reqVo.getPageNumber(), reqVo.getPageSize())
                .doSelectPageInfo(() -> plgFileService.lambdaQuery()
                        .ge(ObjUtil.isNotEmpty(reqVo.getStartTime()), PlgFile::getCreateTime, reqVo.getStartTime())
                        .le(ObjUtil.isNotEmpty(reqVo.getEndTime()), PlgFile::getCreateTime, reqVo.getEndTime())
                        .eq(ObjUtil.isNotEmpty(reqVo.getPlatform()), PlgFile::getPlatform, reqVo.getPlatform())
                        .eq(ObjUtil.isNotEmpty(reqVo.getUrl()), PlgFile::getUrl, reqVo.getUrl())
                        .eq(ObjUtil.isNotEmpty(reqVo.getFilename()), PlgFile::getFilename, reqVo.getFilename())
                        .eq(ObjUtil.isNotEmpty(reqVo.getObjectId()), PlgFile::getObjectId, reqVo.getObjectId())
                        .eq(ObjUtil.isNotEmpty(reqVo.getObjectType()), PlgFile::getObjectType, reqVo.getObjectType())
                        .orderByDesc(PlgFile::getCreateTime)
                        .list());
        List<PlgFilePageResVo> voList = PlgFileConvert.INSTANCE.poToPageResVo(pageInfo.getList());
        return new PageResult<>((int) pageInfo.getTotal(), voList);
    }

    @ApiOperation("删除记录-批量")
    @PostMapping("record/delete")
    public boolean delete(@RequestBody @NotEmpty List<Long> idList) {
        return plgFileService.removeBatchByIds(idList);
    }

    @ApiOperation("通用上传")
    @PostMapping("storage/upload")
    public FileInfo upload(@RequestPart MultipartFile file) {
        return fileStorageService.of(file).upload();
    }

    @ApiOperation("通用上传-批量")
    @PostMapping("storage/upload_files")
    public List<FileInfo> uploadFiles(@RequestPart MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::upload)
                .collect(Collectors.toList());
    }

    @ApiOperation("通用上传图片")
    @PostMapping("storage/upload_image")
    public FileInfo uploadImage(@RequestPart MultipartFile file,
                                @RequestPart(required = false) Integer width,
                                @RequestPart(required = false) Integer height,
                                @RequestPart(required = false) Integer thWidth,
                                @RequestPart(required = false) Integer thHeight) {
        UploadPretreatment uploadPretreatment = fileStorageService.of(file);
        // 根据传入条件设置宽高和缩略
        if (!ObjUtil.hasNull(width, height)) {
            uploadPretreatment.image(img -> img.size(width, height));
        }
        if (!ObjUtil.hasNull(thWidth, thHeight)) {
            uploadPretreatment.thumbnail(th -> th.size(thWidth, thHeight));
        }
        return uploadPretreatment.upload();
    }

    @ApiOperation("通用上传图片-批量")
    @PostMapping("storage/upload_images")
    public List<FileInfo> uploadImages(@RequestPart MultipartFile[] files,
                                       @RequestPart(required = false) Integer width,
                                       @RequestPart(required = false) Integer height,
                                       @RequestPart(required = false) Integer thWidth,
                                       @RequestPart(required = false) Integer thHeight) {
        return Arrays.stream(files)
                .map(o -> uploadImage(o, width, height, thWidth, thHeight))
                .collect(Collectors.toList());
    }

    @ApiOperation("通用下载")
    @GetMapping("storage/download")
    public void download(HttpServletResponse response,
                         @RequestParam String url) {
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(url);
        fileStorageService.download(fileInfo).inputStream(in -> ServletUtil.write(response, in));
    }

    @ApiOperation("通用临时下载")
    @GetMapping("storage/download_temp")
    public void downloadTemp(HttpServletResponse response,
                             @RequestParam String url) {
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(url);
        fileStorageService.download(fileInfo).inputStream(in -> ServletUtil.write(response, in));
        fileStorageService.delete(fileInfo);
    }

    @ApiLogger(type = LogTypeConst.REMOVE, enableSave = true)
    @ApiOperation("通用删除文件-byUrl")
    @PostMapping("storage/delete_by_url")
    public boolean deleteFile(@RequestParam String url) {
        return fileStorageService.delete(url);
    }

    @ApiLogger(type = LogTypeConst.REMOVE, enableSave = true)
    @ApiOperation("通用删除文件-byId")
    @PostMapping("storage/delete_by_id")
    public List<FileInfo> deleteFiles(@RequestBody List<Long> idList) {
        List<PlgFile> plgFileList = plgFileService.listByIds(idList);
        List<FileInfo> fileInfoList = PlgFileConvert.INSTANCE.poToFileInfo(plgFileList);
        return fileInfoList.stream()
                .filter(o -> {
                    try {
                        return fileStorageService.delete(o);
                    } catch (Exception e) {
                        log.error("Delete files by id", e);
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
}
