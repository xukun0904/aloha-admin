package fun.xukun.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.xukun.common.exception.ExceptionCast;
import fun.xukun.common.model.constant.StringPool;
import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.CommonCode;
import fun.xukun.common.model.response.PageResponse;
import fun.xukun.common.util.ObjectUtils;
import fun.xukun.common.util.PageUtils;
import fun.xukun.common.util.StringUtils;
import fun.xukun.model.domain.system.Role;
import fun.xukun.model.domain.system.RoleMenu;
import fun.xukun.model.domain.system.UserRole;
import fun.xukun.model.domain.system.ext.RoleExt;
import fun.xukun.model.manager.RoleManager;
import fun.xukun.model.manager.RoleMenuManager;
import fun.xukun.model.manager.UserRoleManager;
import fun.xukun.platform.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 日期:2020/6/9
 * 角色服务实现类
 *
 * @author xukun
 * @version 1.00
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@CacheConfig(cacheNames = "system")
public class RoleServiceImpl implements RoleService {

    private final RoleManager roleManager;

    private final RoleMenuManager roleMenuManager;

    private final UserRoleManager userRoleManager;

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public PageResponse<RoleExt> pageRoles(Role role, PageRequest pageRequest) {
        // 开始分页
        IPage<Role> page = PageUtils.getPage(pageRequest);
        // 模糊角色名
        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotBlank(role.getName()), Role::getName, role.getName());
        IPage<Role> rolePage = roleManager.page(page, wrapper);
        // 转化成RoleExt分页对象
        IPage<RoleExt> roleExtPage = new Page<>();
        roleExtPage.setTotal(rolePage.getTotal());
        // 查找角色与菜单关系表
        List<Role> records = rolePage.getRecords();
        List<String> roleIds = records.stream().map(Role::getId).collect(Collectors.toList());
        LambdaQueryWrapper<RoleMenu> roleMenuWrapper = Wrappers.lambdaQuery();
        roleMenuWrapper.in(RoleMenu::getRoleId, roleIds);
        List<RoleMenu> rms = roleMenuManager.list(roleMenuWrapper);
        // 封装成RoleExt对象
        List<RoleExt> res = new ArrayList<>();
        for (Role record : records) {
            // 查找所有对应菜单集合用逗号分隔
            String menuIds = rms.stream().filter(rm -> rm.getRoleId().equals(record.getId())).map(RoleMenu::getMenuId)
                    .collect(Collectors.joining(StringPool.COMMA));
            RoleExt target = new RoleExt();
            target.setMenuIds(menuIds);
            ObjectUtils.copyProperties(record, target);
            res.add(target);
        }
        roleExtPage.setRecords(res);
        return PageUtils.convertPageResponse(roleExtPage);
    }

    @CacheEvict(allEntries = true, beforeInvocation = true)
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

    @CacheEvict(allEntries = true, beforeInvocation = true)
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

    @CacheEvict(allEntries = true, beforeInvocation = true)
    @Override
    public void multipleDelete(String ids) {
        List<String> idList = StringUtils.split(ids, StringPool.COMMA);
        boolean isSuccess = this.roleManager.removeByIds(idList);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.SAVE_FAIL);
        }
        // 删除菜单与角色关系
        LambdaUpdateWrapper<RoleMenu> queryWrapper = Wrappers.lambdaUpdate();
        queryWrapper.in(RoleMenu::getRoleId, idList);
        this.roleMenuManager.remove(queryWrapper);
        // 删除用户与角色关系
        LambdaUpdateWrapper<UserRole> userWrapper = Wrappers.lambdaUpdate();
        userWrapper.in(UserRole::getRoleId, idList);
        userRoleManager.remove(userWrapper);
    }

    @Cacheable(key = "'role:list'", unless = "#result == null")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<Role> listRoles() {
        return this.roleManager.list();
    }

    @Cacheable(key = "'role:perms:'+#roleIds", unless = "#result == null")
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

    @Cacheable(key = "'role:list:'+#userId", unless = "#result == null")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<Role> listByUserId(String userId) {
        return this.roleManager.getBaseMapper().listByUserId(userId);
    }
}
