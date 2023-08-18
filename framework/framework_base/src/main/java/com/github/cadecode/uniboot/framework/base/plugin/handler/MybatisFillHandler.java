package com.github.cadecode.uniboot.framework.base.plugin.handler;

import com.github.cadecode.uniboot.common.plugin.mybatis.handler.AbstractObjectFillHandler;
import com.github.cadecode.uniboot.framework.base.util.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * mybatis plus 对象填充处理器
 *
 * @author Cade Li
 * @since 2023/6/8
 */
@Component
public class MybatisFillHandler extends AbstractObjectFillHandler {
    @Override
    public void updateUser(MetaObject metaObject) {
        this.setFieldValByName("updateUser", SecurityUtil.getUsername(), metaObject);
    }
}
