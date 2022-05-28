package top.cadecode.sra.system.bean.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author Cade Li
 * @date 2022/5/24
 * @description 系统接口 VO
 */
@Data
public class SysApiVo {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 接口 url
     */
    private String url;

    /**
     * 描述
     */
    private String description;
}
