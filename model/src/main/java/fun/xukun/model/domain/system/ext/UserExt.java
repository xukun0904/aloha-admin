package fun.xukun.model.domain.system.ext;

import fun.xukun.model.domain.system.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日期:2020/6/22
 * 用户扩展类
 *
 * @author xukun
 * @version 1.00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserExt extends User {

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 关联角色主键
     */
    private String roleIds;

    /**
     * 关联角色名
     */
    private String roleNames;
}
