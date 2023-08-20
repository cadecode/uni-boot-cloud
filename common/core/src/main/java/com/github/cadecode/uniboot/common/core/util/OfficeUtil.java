package com.github.cadecode.uniboot.common.core.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelFileUtil;
import cn.hutool.poi.excel.sax.ExcelSaxReader;
import cn.hutool.poi.excel.sax.ExcelSaxUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.github.cadecode.uniboot.common.core.annotation.ExcelField;
import lombok.Data;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Office 工具类，基于 hutool-poi，提供注解支持
 * Excel 基于 ExcelSaxUtil 和 BeanUtil
 *
 * @author Cade Li
 * @since 2023/6/9
 */
public class OfficeUtil {

    public static <T> ExcelReadHelper<T> excelRead(String excelPath, Class<T> clazz) {
        return ExcelReadHelper.create(FileUtil.getInputStream(excelPath), clazz);
    }

    public static <T> ExcelReadHelper<T> excelRead(File excelFile, Class<T> clazz) {
        return ExcelReadHelper.create(FileUtil.getInputStream(excelFile), clazz);
    }

    public static <T> ExcelReadHelper<T> excelRead(InputStream excelIn, Class<T> clazz) {
        return ExcelReadHelper.create(excelIn, clazz);
    }

    @Data
    public static class ExcelReadHelper<T> {
        private InputStream inputStream;
        private Class<T> clazz;
        private Integer sheetIndex;
        /**
         * 首行是否存在表头
         */
        private Boolean hasHead;
        private ExcelRowHandler<T> rowHandler;
        /**
         * 根据首行表头获取的列序和表头映射关系
         * 当不存在表头时，存储的是列序和列序的字符串形式
         */
        private Map<Integer, String> indexHeadMap;
        /**
         * 根据注解 headAlias 获取的表头和字段名的映射关系
         * 不存在 headAlias 时根据 colIndex 和 indexHeadMap 进行合并得出
         */
        private Map<String, String> headFieldMap = new HashMap<>();
        /**
         * 需要忽略的字段，没有 ExcelField 注解的 / 注解 ignore 为 true 的
         */
        private List<String> ignoredFields = new ArrayList<>();
        /**
         * BeanUtil 拷贝配置项
         */
        private CopyOptions copyOptions = CopyOptions.create();

        public static <T> ExcelReadHelper<T> create(InputStream inputStream, Class<T> clazz) {
            ExcelReadHelper<T> reader = new ExcelReadHelper<>();
            reader.setInputStream(inputStream);
            reader.setClazz(clazz);
            return reader;
        }

        // builder methods

        public ExcelReadHelper<T> sheetIndex(Integer sheetIndex) {
            setSheetIndex(sheetIndex);
            return this;
        }

        public ExcelReadHelper<T> hasHead(Boolean hasHead) {
            setHasHead(hasHead);
            return this;
        }

        public ExcelReadHelper<T> rowHandler(ExcelRowHandler<T> rowHandler) {
            setRowHandler(rowHandler);
            return this;
        }

        public void read() {
            checkAndInitParams();
            // hutool sax read
            ExcelSaxReader<?> reader = ExcelSaxUtil.createSaxReader(ExcelFileUtil.isXlsx(inputStream), new RowHandler() {
                @Override
                public void handle(int sheetIndex, long rowIndex, List<Object> rowCells) {
                    try {
                        // 判断是否要根据首行获取 indexHeadMap
                        if (initIndexHeadMap(rowCells)) {
                            geneHeadFieldMap();
                            geneCopyOptions();
                            // 存在 head 第一行解析完成后 return
                            if (ObjUtil.equal(hasHead, true)) {
                                return;
                            }
                        }
                        // rowCells 转为 Map 结构
                        Map<String, Object> rowMap = cellsToMap(rowCells);
                        T bean = BeanUtil.toBean(rowMap, clazz, copyOptions);
                        rowHandler.handle(sheetIndex, rowIndex, bean);
                    } catch (Exception e) {
                        rowHandler.onFailed(sheetIndex, rowIndex, e);
                    }
                }

                @Override
                public void doAfterAllAnalysed() {
                    rowHandler.doAfterAll();
                }
            });
            reader.read(inputStream, sheetIndex);
        }

        private void checkAndInitParams() {
            if (ObjUtil.isNull(sheetIndex)) {
                // 默认读取首个 sheet
                sheetIndex = 0;
            }
            if (ObjUtil.isNull(hasHead)) {
                // 默认有表头
                hasHead = true;
            }
            if (ObjUtil.isNull(rowHandler)) {
                throw new RuntimeException("Can not read without rowHandler");
            }
        }

        private boolean initIndexHeadMap(List<Object> rowCells) {
            if (ObjUtil.isNotNull(indexHeadMap)) {
                return false;
            }
            AtomicInteger tmpIdx = new AtomicInteger();
            indexHeadMap = rowCells.stream()
                    .collect(Collectors.toMap(o -> tmpIdx.getAndIncrement(), o -> {
                        // 有表头时使用表头映射
                        if (ObjUtil.equal(hasHead, true)) {
                            return String.valueOf(o);
                        }
                        // 无表头时使用列序映射
                        return String.valueOf(tmpIdx.get() - 1);
                    }));
            return true;
        }

        private void geneHeadFieldMap() {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                // 若不存在 ExcelField 注解，加入忽略
                if (ObjUtil.isNull(excelField)) {
                    ignoredFields.add(field.getName());
                    continue;
                }
                // 若开启读忽略，加入忽略
                if (excelField.readIgnore()) {
                    ignoredFields.add(field.getName());
                    continue;
                }
                // 若存在表头，且 headAlias 不为空
                if (hasHead && ObjUtil.isNotEmpty(excelField.headAlias())) {
                    headFieldMap.put(excelField.headAlias(), field.getName());
                    continue;
                }
                // 若存在 ExcelField 注解，但是不存在表头或 headAlias
                // 判断是否设置 colIndex，是否溢出
                if (excelField.colIndex() != -1 && indexHeadMap.size() > excelField.colIndex()) {
                    // 从 indexHeadMap 获取对应的 head，putIfAbsent 不允许覆盖
                    headFieldMap.putIfAbsent(indexHeadMap.get(excelField.colIndex()), field.getName());
                }
            }
        }

        private void geneCopyOptions() {
            copyOptions.setIgnoreProperties(ignoredFields.toArray(new String[0]))
                    .setFieldMapping(headFieldMap);
        }

        private Map<String, Object> cellsToMap(List<Object> rowCells) {
            AtomicInteger tmpIdx = new AtomicInteger();
            return rowCells.stream()
                    .collect(Collectors.toMap(o -> indexHeadMap.get(tmpIdx.getAndIncrement()), o -> o, (k1, k2) -> k1));
        }

        public List<T> readAll() {
            List<T> beanList = new ArrayList<>();
            ExcelReadHelper<T> readHelper = create(inputStream, clazz)
                    .sheetIndex(sheetIndex)
                    .hasHead(hasHead);
            ExcelRowHandler<T> newRowHandler;
            if (ObjUtil.isNull(rowHandler)) {
                readHelper.rowHandler((sheetIndex, rowIndex, bean) -> beanList.add(bean)).read();
                return beanList;
            }
            // 复用设置的 rowHandler
            newRowHandler = new ExcelRowHandler<T>() {
                @Override
                public void doAfterAll() {
                    rowHandler.doAfterAll();
                }

                @Override
                public void onFailed(int sheetIndex, long rowIndex, Exception e) {
                    rowHandler.onFailed(sheetIndex, rowIndex, e);
                }

                @Override
                public void handle(int sheetIndex, long rowIndex, T bean) {
                    rowHandler.handle(sheetIndex, rowIndex, bean);
                    beanList.add(bean);
                }
            };
            readHelper.rowHandler(newRowHandler).read();
            return beanList;
        }
    }

    /**
     * Excel 行处理器
     * onFailed 默认抛出包装的 RuntimeException
     * 如果需要异常时不中断处理，重写 onFailed 方法即可
     */
    public interface ExcelRowHandler<T> {

        void handle(int sheetIndex, long rowIndex, T bean);

        default void doAfterAll() {
        }

        default void onFailed(int sheetIndex, long rowIndex, Exception e) {
            throw new RuntimeException(StrUtil.format("Excel row handler failed at sheet:{} row:{}", sheetIndex, rowIndex), e);
        }
    }
}
