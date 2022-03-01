package top.cadecode.sa.framework.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * @author Cade Li
 * @date 2021/12/10
 * @description 用户信息 VO
 */
@Data
public class SecurityUserVo {

    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String nickName;
    @JsonIgnore
    private boolean enableFlag;
    private String token;
    private String refreshToken;
    private List<String> roles;

}
