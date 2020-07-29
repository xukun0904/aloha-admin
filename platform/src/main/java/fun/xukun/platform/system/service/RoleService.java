package fun.xukun.platform.system.service;

import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.PageResponse;
import fun.xukun.model.domain.system.Role;
import fun.xukun.model.domain.system.ext.RoleExt;

import java.util.List;

/**
 * 日期:2020/6/9
 * 角色服务接口
 *
 * @author xukun
 * @version 1.00
 */
public interface RoleService {
    /**
     * 分页查询
     *
     * @param role        查询条件
     * @param pageRequest 分页参数
     * @return 分页结果
     */
    PageResponse<RoleExt> pageRoles(Role role, PageRequest pageRequest);

    /**
     * 更新
     *
     * @param role 实体类
     */
    void update(RoleExt role);

    /**
     * 插入
     *
     * @param role 实体类
     */
    void insert(RoleExt role);

    /**
     * 批量删除
     *
     * @param ids 主键
     */
    void multipleDelete(String ids);

    /**
     * 查询所有
     *
     * @return 集合
     */
    List<Role> listRoles();

    /**
     * 根据角色主键获取权限列表
     *
     * @param roleIds 角色主键集合
     * @return 权限字符串列表
     */
    List<String> listPermissionByRoleIds(String roleIds);

    /**
     * 根据用户主键获取角色列表
     *
     * @param userId 用户主键
     * @return 角色列表
     */
    List<Role> listByUserId(String userId);
}
