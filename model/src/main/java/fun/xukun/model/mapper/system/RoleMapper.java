package fun.xukun.model.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.xukun.model.domain.system.Role;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author xukun
 * @since 2020-06-19
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色主键获取权限列表
     *
     * @param roleIdList 角色主键集合
     * @return 权限字符串列表
     */
    List<String> listPermissionByRoleIds(List<String> roleIdList);

    /**
     * 根据用户主键获取角色列表
     *
     * @param userId 用户主键
     * @return 角色列表
     */
    List<Role> listByUserId(String userId);
}
