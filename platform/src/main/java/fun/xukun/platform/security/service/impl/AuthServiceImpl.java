package fun.xukun.platform.security.service.impl;

import fun.xukun.common.model.constant.Constants;
import fun.xukun.common.util.CookieUtils;
import fun.xukun.model.domain.system.ext.UserExt;
import fun.xukun.model.domain.system.response.AuthCode;
import fun.xukun.platform.config.AlohaProperties;
import fun.xukun.platform.security.config.JwtProperties;
import fun.xukun.platform.security.jwt.JwtComponent;
import fun.xukun.platform.security.model.UserInfo;
import fun.xukun.platform.security.service.AuthService;
import fun.xukun.platform.system.service.RoleService;
import fun.xukun.platform.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 日期:2020/6/28
 *
 * @author xukun
 * @version 1.00
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final RoleService roleService;

    private final AlohaProperties alohaProperties;

    private final JwtComponent jwtComponent;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserExt userExt = userService.getByUsername(username);
        if (userExt == null) {
            throw new UsernameNotFoundException(AuthCode.USER_INFO_INCORRECT.message());
        }
        if (Constants.USER_STATUS_LOCKED == userExt.getStatus()) {
            throw new LockedException(AuthCode.USER_LOCKED.message());
        }
        List<GrantedAuthority> authorities = getAuthority(userExt.getRoleIds());
        return new UserInfo(userExt.getId(), userExt.getUsername(), userExt.getPassword(), userExt.getIsTab(), userExt.getTheme(), userExt.getNickName(), userExt.getStatus(), authorities);
    }

    private List<GrantedAuthority> getAuthority(String roleIds) {
        // 根据角色ID获取对应功能权限
        List<String> permissions = roleService.listPermissionByRoleIds(roleIds);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    @Override
    public UserInfo getCurrentUserInfo(HttpServletRequest request) {
        JwtProperties jwt = alohaProperties.getJwt();
        // token cookie名称
        String cookieName = jwt.getCookieName();
        // 获取token值
        String authToken = CookieUtils.getCookieValue(request, cookieName);
        // 从token中获取用户信息
        return jwtComponent.getInfoFromToken(authToken);
    }
}
