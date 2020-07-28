package fun.xukun.platform.security.jwt;

import fun.xukun.common.model.constant.JwtConstants;
import fun.xukun.common.model.constant.StringPool;
import fun.xukun.common.util.ObjectUtils;
import fun.xukun.platform.config.AlohaProperties;
import fun.xukun.platform.security.model.UserInfo;
import fun.xukun.platform.system.service.RoleService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by IntelliJ IDEA.
 * jwt操作类
 *
 * @author XK
 * @version 1.0
 * @date 2018年09月28日
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtComponent {

    private final AlohaProperties properties;

    private final RoleService roleService;

    /**
     * 私钥加密token
     *
     * @param userInfo 载荷中的数据
     */
    public String generateToken(UserInfo userInfo) {
        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        String permissions = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(StringPool.COMMA));
        return Jwts.builder().claim(JwtConstants.JWT_KEY_ID, userInfo.getId()).claim(JwtConstants.JWT_KEY_USERNAME, userInfo.getUsername())
        .claim(JwtConstants.JWT_KEY_PASSWORD, userInfo.getPassword()).claim(JwtConstants.JWT_KEY_IS_TAB, userInfo.getIsTab())
        .claim(JwtConstants.JWT_KEY_THEME, userInfo.getTheme()).claim(JwtConstants.JWT_KEY_NICK_NAME, userInfo.getNickName())
        .claim(JwtConstants.JWT_KEY_STATUS, userInfo.getStatus()).claim(JwtConstants.JWT_KEY_AUTHORITIES, permissions)
        .claim(JwtConstants.JWT_KEY_ROLE_IDS, userInfo.getRoleIds()).setExpiration(Date.from(LocalDateTime.now()
        .plusMinutes(properties.getJwt().getExpire()).atZone(ZoneId.systemDefault()).toInstant())).signWith(SignatureAlgorithm.HS512, properties.getJwt().getSecret()).compact();
    }


    /**
     * 密钥解析token
     *
     * @param token 用户请求中的token
     * @return 解析后的jwt对象
     */
    private Jws<Claims> parserToken(String token) {
        return Jwts.parser().setSigningKey(properties.getJwt().getSecret()).parseClaimsJws(token);
    }

    /**
     * 校验token是否有效
     *
     * @param token 用户请求中的token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(properties.getJwt().getSecret()).parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("无效的JWTToken");
        } catch (ExpiredJwtException e) {
            log.info("JWTToken已过期");
        } catch (UnsupportedJwtException e) {
            log.info("不支持的JWTToken");
        } catch (IllegalArgumentException e) {
            log.info("JWTToken为空");
        }
        return false;
    }

    /**
     * 获取token中的用户信息
     *
     * @param token 用户请求中的令牌
     * @return 用户信息
     */
    public UserInfo getInfoFromToken(String token) {
        Jws<Claims> claimsJws = parserToken(token);
        Claims body = claimsJws.getBody();
        // 实时获取权限
        String roleIds = ObjectUtils.toString(body.get(JwtConstants.JWT_KEY_ROLE_IDS));
        List<String> permissions = roleService.listPermissionByRoleIds(roleIds);
        Collection<? extends GrantedAuthority> authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UserInfo(ObjectUtils.toString(body.get(JwtConstants.JWT_KEY_ID)), ObjectUtils.toString(body.get(JwtConstants.JWT_KEY_USERNAME)),
        ObjectUtils.toString(body.get(JwtConstants.JWT_KEY_PASSWORD)), ObjectUtils.toInt(body.get(JwtConstants.JWT_KEY_IS_TAB)),
        ObjectUtils.toInt(body.get(JwtConstants.JWT_KEY_THEME)), ObjectUtils.toString(body.get(JwtConstants.JWT_KEY_NICK_NAME)),
        ObjectUtils.toInt(body.get(JwtConstants.JWT_KEY_STATUS)), authorities, roleIds);
    }
}