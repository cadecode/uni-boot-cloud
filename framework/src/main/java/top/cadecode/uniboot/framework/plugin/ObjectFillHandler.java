package top.cadecode.uniboot.framework.plugin;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import top.cadecode.uniboot.common.plugin.mybatis.handler.AbstractObjectFillHandler;
import top.cadecode.uniboot.framework.util.SecurityUtil;

/**
 * 对象填充处理器
 *
 * @author Cade Li
 * @since 2023/6/8
 */
@Component
public class ObjectFillHandler extends AbstractObjectFillHandler {
    @Override
    public void updateUser(MetaObject metaObject) {
        this.setFieldValByName("updateUser", SecurityUtil.getUsername(), metaObject);
    }
}
