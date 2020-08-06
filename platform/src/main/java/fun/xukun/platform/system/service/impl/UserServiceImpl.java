package fun.xukun.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fun.xukun.common.exception.ExceptionCast;
import fun.xukun.common.model.constant.Constants;
import fun.xukun.common.model.constant.StringPool;
import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.CommonCode;
import fun.xukun.common.model.response.PageResponse;
import fun.xukun.common.util.PageUtils;
import fun.xukun.common.util.StringUtils;
import fun.xukun.model.domain.system.Role;
import fun.xukun.model.domain.system.User;
import fun.xukun.model.domain.system.UserRole;
import fun.xukun.model.domain.system.ext.UserExt;
import fun.xukun.model.domain.system.request.UserQuery;
import fun.xukun.model.domain.system.response.AuthCode;
import fun.xukun.model.manager.system.UserManager;
import fun.xukun.model.manager.system.UserRoleManager;
import fun.xukun.platform.security.model.UserInfo;
import fun.xukun.platform.system.service.RoleService;
import fun.xukun.platform.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日期:2020/6/22
 * 用户服务实现类
 *
 * @author xukun
 * @version 1.00
 */
@CacheConfig(cacheNames = "system")
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserManager userManager;

    private final UserRoleManager userRoleManager;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    @CacheEvict(allEntries = true, beforeInvocation = true)
    @Override
    public void update(UserExt bean) {
        // 设置更新时间
        bean.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = this.userManager.updateById(bean);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.UPDATE_FAIL);
        }
        // 重新保存用户角色关系
        LambdaQueryWrapper<UserRole> removeWrapper = Wrappers.lambdaQuery();
        removeWrapper.eq(UserRole::getUserId, bean.getId());
        this.userRoleManager.remove(removeWrapper);
        saveUserRoles(bean, bean.getRoleIds());
    }

    @CacheEvict(allEntries = true, beforeInvocation = true)
    @Override
    public void insert(UserExt bean) {
        bean.setId(null);
        bean.setCreateTime(LocalDateTime.now());
        bean.setPassword(passwordEncoder.encode(Constants.DEFAULT_PASSWORD));
        boolean isSuccess = this.userManager.save(bean);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.SAVE_FAIL);
        }
        saveUserRoles(bean, bean.getRoleIds());
    }

    private void saveUserRoles(UserExt bean, String roleIds) {
        List<String> idList = StringUtils.split(roleIds, StringPool.COMMA);
        List<UserRole> userRoles = new ArrayList<>();
        for (String id : idList) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(id);
            userRole.setUserId(bean.getId());
            userRoles.add(userRole);
        }
        this.userRoleManager.saveBatch(userRoles);
    }

    @CacheEvict(allEntries = true, beforeInvocation = true)
    @Override
    public void multipleDelete(String ids) {
        List<String> idList = StringUtils.split(ids, StringPool.COMMA);
        // 删除
        this.userManager.removeByIds(idList);
        // 删除用户角色关系
        LambdaQueryWrapper<UserRole> removeWrapper = Wrappers.lambdaQuery();
        removeWrapper.in(UserRole::getUserId, idList);
        this.userRoleManager.remove(removeWrapper);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public PageResponse<UserExt> list(UserQuery query, PageRequest pageRequest) {
        // 分页查询，默认根据主键倒序
        IPage<User> page = PageUtils.getPage(pageRequest);
        IPage<UserExt> userPage = userManager.listUsers(page, query);
        return PageUtils.convertPageResponse(userPage);
    }

    @Cacheable(key = "'user:'+#id", unless = "#result == null")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public UserExt getById(String id) {
        UserExt userExt = userManager.getUserById(id);
        return obtainUserExt(userExt);
    }

    private UserExt obtainUserExt(UserExt userExt) {
        // 根据用户主键查询角色列表
        List<Role> roles = roleService.listByUserId(userExt.getId());
        String roleIds = roles.stream().map(Role::getId).collect(Collectors.joining(StringPool.COMMA));
        String roleNames = roles.stream().map(Role::getName).collect(Collectors.joining(StringPool.COMMA));
        // 赋值
        userExt.setRoleIds(roleIds);
        userExt.setRoleNames(roleNames);
        return userExt;
    }

    @CacheEvict(cacheNames = "system:user", allEntries = true, beforeInvocation = true)
    @Override
    public void updatePatch(User bean) {
        // 设置更新时间
        bean.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = this.userManager.updateById(bean);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.UPDATE_FAIL);
        }
    }

    @Cacheable(key = "'user:'+#username", unless = "#result == null")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public UserExt getByUsername(String username) {
        UserExt userExt = userManager.getByUsername(username);
        return obtainUserExt(userExt);
    }

    @CacheEvict(allEntries = true, beforeInvocation = true)
    @Override
    public void updatePassword(UserInfo currentUser, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            ExceptionCast.cast(AuthCode.OLD_PASSWORD_INCORRECT);
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(newPassword));
        LambdaUpdateWrapper<User> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(User::getUsername, currentUser.getUsername());
        this.userManager.getBaseMapper().update(user, wrapper);
    }

    @Cacheable(key = "'user:verify:'+#username+'_'+#id", unless = "#result == null")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public boolean verify(String username, String id) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getUsername, username);
        wrapper.ne(StringUtils.isNotBlank(id), User::getId, id);
        return this.userManager.count(wrapper) <= 0;
    }

    @CacheEvict(cacheNames = "system:user", allEntries = true, beforeInvocation = true)
    @Override
    public void updateSettings(User bean) {
        User record = new User();
        record.setId(bean.getId());
        record.setTheme(bean.getTheme());
        record.setIsTab(bean.getIsTab());
        // 设置更新时间
        record.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = this.userManager.updateById(record);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.UPDATE_FAIL);
        }
    }
}
