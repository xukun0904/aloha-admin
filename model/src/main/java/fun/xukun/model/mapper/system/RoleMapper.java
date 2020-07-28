package fun.xukun.model.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fun.xukun.model.domain.system.Role;
import fun.xukun.model.domain.system.ext.RoleExt;
import org.apache.ibatis.annotations.Param;

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
     * 分页查询角色列表
     *
     * @param page 分页参数
     * @param role 查询参数
     * @return 角色列表
     */
    IPage<RoleExt> listRoles(IPage<Role> page, @Param("role") Role role);

    /**
     * 根据角色主键获取权限列表
     *
     * @param roleIdList 角色主键集合
     * @return 权限字符串列表
     */
    List<String> listPermissionByRoleIds(List<String> roleIdList);
}
