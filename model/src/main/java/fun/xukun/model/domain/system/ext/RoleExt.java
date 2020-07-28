package fun.xukun.model.domain.system.ext;

import fun.xukun.model.domain.system.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日期:2020/6/19
 * 角色扩展类
 *
 * @author xukun
 * @version 1.00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleExt extends Role {
    /**
     * 菜单主键
     */
    private String menuIds;
}
