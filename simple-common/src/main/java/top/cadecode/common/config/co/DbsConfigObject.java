package top.cadecode.common.config.co;

import lombok.Data;

/**
 * @author Cade Li
 * @date 2021/12/4
 * @description 数据库配置对象类
 */
@Data
public class DbsConfigObject {
    private String name;
    private String type;
    private boolean defaultFlag;
}
