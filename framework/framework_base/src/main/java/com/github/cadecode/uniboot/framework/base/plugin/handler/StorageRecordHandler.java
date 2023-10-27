package com.github.cadecode.uniboot.framework.base.plugin.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.cadecode.uniboot.common.plugin.storage.handler.AbstractStorageHandler;
import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgFile;
import com.github.cadecode.uniboot.framework.base.plugin.convert.PlgFileConvert;
import com.github.cadecode.uniboot.framework.base.plugin.service.PlgFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.stereotype.Component;

/**
 * 文件存储记录处理器
 *
 * @author Cade Li
 * @since 2023/10/26
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class StorageRecordHandler extends AbstractStorageHandler {

    private final PlgFileService plgFileService;

    @Override
    public boolean save(FileInfo fileInfo) {
        PlgFile plgFile = PlgFileConvert.INSTANCE.fileInfoToPo(fileInfo);
        boolean saveFlag = plgFileService.save(plgFile);
        if (saveFlag) {
            // 注入 id
            fileInfo.setId(String.valueOf(plgFile.getId()));
        }
        return saveFlag;
    }

    @Override
    public FileInfo getByUrl(String url) {
        // 按 url 查询
        PlgFile plgFile = plgFileService.lambdaQuery()
                .eq(PlgFile::getUrl, url)
                .one();
        return PlgFileConvert.INSTANCE.poToFileInfo(plgFile);
    }

    @Override
    public boolean delete(String url) {
        return plgFileService.remove(Wrappers.<PlgFile>lambdaQuery().eq(PlgFile::getUrl, url));
    }
}
