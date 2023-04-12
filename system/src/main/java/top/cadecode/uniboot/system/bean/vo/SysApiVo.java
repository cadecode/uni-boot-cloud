package top.cadecode.uniboot.system.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * 系统接口 VO
 *
 * @author Cade Li
 * @date 2022/5/24
 */
@Data
public class SysApiVo {

    private Long id;

    private String url;

    private String description;

    private List<String> roles;
}
