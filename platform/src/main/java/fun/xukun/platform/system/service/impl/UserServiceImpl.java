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
import fun.xukun.model.domain.system.User;
import fun.xukun.model.domain.system.UserRole;
import fun.xukun.model.domain.system.ext.UserExt;
import fun.xukun.model.domain.system.request.UserQuery;
import fun.xukun.model.domain.system.response.AuthCode;
import fun.xukun.platform.security.model.UserInfo;
import fun.xukun.platform.system.manager.UserManager;
import fun.xukun.platform.system.manager.UserRoleManager;
import fun.xukun.platform.system.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 日期:2020/6/22
 * 用户服务实现类
 *
 * @author xukun
 * @version 1.00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private final UserManager manager;

    private final UserRoleManager userRoleManager;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserManager manager, UserRoleManager userRoleManager, PasswordEncoder passwordEncoder) {
        this.manager = manager;
        this.userRoleManager = userRoleManager;
        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(cacheNames = {"user_cache"}, allEntries = true, beforeInvocation = true)
    @Override
    public void update(UserExt bean) {
        // 设置更新时间
        bean.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = this.manager.updateById(bean);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.UPDATE_FAIL);
        }
        // 重新保存用户角色关系
        LambdaQueryWrapper<UserRole> removeWrapper = Wrappers.lambdaQuery();
        removeWrapper.eq(UserRole::getUserId, bean.getId());
        this.userRoleManager.remove(removeWrapper);
        saveUserRoles(bean, bean.getRoleIds());
    }

    @Override
    public void insert(UserExt bean) {
        bean.setId(null);
        bean.setCreateTime(LocalDateTime.now());
        bean.setPassword(passwordEncoder.encode(Constants.DEFAULT_PASSWORD));
        boolean isSuccess = this.manager.save(bean);
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

    @CacheEvict(cacheNames = "user_cache", allEntries = true, beforeInvocation = true)
    @Override
    public void multipleDelete(String ids) {
        List<String> idList = StringUtils.split(ids, StringPool.COMMA);
        // 删除
        this.manager.removeByIds(idList);
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
        IPage<UserExt> userPage = manager.listUsers(page, query);
        return PageUtils.convertPageResponse(userPage);
    }

    @Cacheable(cacheNames = "user_cache", key = "'user_' + #id")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public UserExt getById(String id) {
        return manager.getUserById(id);
    }

    @Override
    public void updatePatch(User bean) {
        // 设置更新时间
        bean.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = this.manager.updateById(bean);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.UPDATE_FAIL);
        }
    }

    @Cacheable(cacheNames = "user_cache", key = "'user_' + #username")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public UserExt getByUsername(String username) {
        return manager.getByUsername(username);
    }

    @CacheEvict(cacheNames = "user_cache", allEntries = true, beforeInvocation = true)
    @Override
    public void updatePassword(UserInfo currentUser, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            ExceptionCast.cast(AuthCode.OLD_PASSWORD_INCORRECT);
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(newPassword));
        LambdaUpdateWrapper<User> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(User::getUsername, currentUser.getUsername());
        this.manager.getBaseMapper().update(user, wrapper);
    }
}
