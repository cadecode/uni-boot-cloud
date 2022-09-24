package top.cadecode.uniboot.framework.convert;

/**
 * 请求参数枚举转换接口
 *
 * @author Cade Li
 * @date 2022/5/28
 */
public interface EnumConvertor {

    /**
     * 返回枚举元素的对应标记
     */
    String convertBy();

    /**
     * 返回用于数据库持久化的字段内容
     * 数据库使用数值类型存储，如 tinyint unsigned 0~255
     */
    Integer persistBy();

}
