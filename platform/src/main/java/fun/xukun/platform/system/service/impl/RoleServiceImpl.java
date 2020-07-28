package fun.xukun.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fun.xukun.common.exception.ExceptionCast;
import fun.xukun.common.model.constant.StringPool;
import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.CommonCode;
import fun.xukun.common.model.response.PageResponse;
import fun.xukun.common.util.PageUtils;
import fun.xukun.common.util.StringUtils;
import fun.xukun.model.domain.system.Role;
import fun.xukun.model.domain.system.RoleMenu;
import fun.xukun.model.domain.system.ext.RoleExt;
import fun.xukun.platform.system.manager.RoleManager;
import fun.xukun.platform.system.manager.RoleMenuManager;
import fun.xukun.platform.system.service.RoleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 日期:2020/6/9
 * 角色服务实现类
 *
 * @author xukun
 * @version 1.00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    private final RoleManager roleManager;

    private final RoleMenuManager roleMenuManager;

    public RoleServiceImpl(RoleManager roleManager, RoleMenuManager roleMenuManager) {
        this.roleManager = roleManager;
        this.roleMenuManager = roleMenuManager;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public PageResponse<RoleExt> pageRoles(Role role, PageRequest pageRequest) {
        IPage<Role> page = PageUtils.getPage(pageRequest);
        IPage<RoleExt> rolePage = roleManager.getBaseMapper().listRoles(page, role);
        return PageUtils.convertPageResponse(rolePage);
    }

    @CacheEvict(cacheNames = {"role_cache"}, allEntries = true, beforeInvocation = true)
    @Override
    public void update(RoleExt role) {
        role.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = this.roleManager.updateById(role);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.UPDATE_FAIL);
        }
        // 删除关系
        LambdaQueryWrapper<RoleMenu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoleMenu::getRoleId, role.getId());
        this.roleMenuManager.remove(queryWrapper);
        // 重新保存
        insertRoleMenus(role.getId(), role.getMenuIds());
    }

    @CacheEvict(cacheNames = "role_cache", allEntries = true, beforeInvocation = true)
    @Override
    public void insert(RoleExt role) {
        role.setId(null);
        role.setCreateTime(LocalDateTime.now());
        boolean isSuccess = this.roleManager.save(role);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.SAVE_FAIL);
        }
        // 保存关系
        insertRoleMenus(role.getId(), role.getMenuIds());
    }

    @CacheEvict(cacheNames = {"role_cache"}, allEntries = true, beforeInvocation = true)
    @Override
    public void multipleDelete(String ids) {
        List<String> idList = StringUtils.split(ids, StringPool.COMMA);
        boolean isSuccess = this.roleManager.removeByIds(idList);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.SAVE_FAIL);
        }
        // 删除关系
        LambdaQueryWrapper<RoleMenu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(RoleMenu::getRoleId, idList);
        this.roleMenuManager.remove(queryWrapper);
    }

    @Cacheable(cacheNames = "role_cache", key = "'roles'")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<Role> listRoles() {
        return this.roleManager.list();
    }

    @Cacheable(cacheNames = "role_cache", key = "'roles_' + #roleIds")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<String> listPermissionByRoleIds(String roleIds) {
        List<String> roleIdList = StringUtils.split(roleIds, StringPool.COMMA);
        return this.roleManager.getBaseMapper().listPermissionByRoleIds(roleIdList);
    }

    private void insertRoleMenus(String roleId, String menuIds) {
        List<String> idList = StringUtils.split(menuIds, StringPool.COMMA);
        List<RoleMenu> roleMenus = new ArrayList<>();
        for (String id : idList) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(id);
            roleMenus.add(roleMenu);
        }
        this.roleMenuManager.saveBatch(roleMenus);
    }
}
