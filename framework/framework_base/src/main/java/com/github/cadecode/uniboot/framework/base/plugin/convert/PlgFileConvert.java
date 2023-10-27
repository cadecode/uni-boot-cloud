package com.github.cadecode.uniboot.framework.base.plugin.convert;

import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgFile;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgFileVo.PlgFilePageResVo;
import org.dromara.x.file.storage.core.FileInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文件记录 bean convert
 *
 * @author Cade Li
 * @since 2023/10/25
 */
@Mapper
public interface PlgFileConvert {

    PlgFileConvert INSTANCE = Mappers.getMapper(PlgFileConvert.class);

    @Mapping(target = "thFileAcl", ignore = true)
    @Mapping(target = "fileAcl", ignore = true)
    FileInfo poToFileInfo(PlgFile po);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    PlgFile fileInfoToPo(FileInfo fileInfo);

    List<PlgFilePageResVo> poToPageResVo(List<PlgFile> poList);
}
