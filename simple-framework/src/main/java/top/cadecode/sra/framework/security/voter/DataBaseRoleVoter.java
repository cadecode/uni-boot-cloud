package top.cadecode.sra.framework.security.voter;

import org.springframework.security.access.vote.RoleVoter;
import org.springframework.stereotype.Component;

/**
 * @author Cade Li
 * @date 2022/5/28
 * @description 基于数据库内容的角色投票器
 */
@Component
public class DataBaseRoleVoter extends RoleVoter {
}
