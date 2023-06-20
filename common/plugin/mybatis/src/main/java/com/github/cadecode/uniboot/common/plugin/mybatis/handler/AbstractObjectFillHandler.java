package com.github.cadecode.uniboot.common.plugin.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 对象填充处理器
 * 重写 updateUser 来实现 更新者 字段的填充
 *
 * @author Cade Li
 * @since 2023/6/8
 */
public abstract class AbstractObjectFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        updateUser(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        updateUser(metaObject);
    }

    public void updateUser(MetaObject metaObject) {

    }
}
