package top.cadecode.uniboot.system.bean.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 系统接口 PO
 *
 * @author Cade Li
 * @date 2022/5/24
 */
@Data
public class SysApi {

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

    private Date createTime;

    private Date updateTime;

    private String updateUser;
}
