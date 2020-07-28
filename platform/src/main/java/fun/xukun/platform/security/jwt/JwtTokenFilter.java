package fun.xukun.platform.security.jwt;

import fun.xukun.common.util.CookieUtils;
import fun.xukun.common.util.StringUtils;
import fun.xukun.platform.security.model.UserInfo;
import fun.xukun.platform.config.AlohaProperties;
import fun.xukun.platform.security.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 日期:2020/6/29
 * jwt过滤器
 *
 * @author xukun
 * @version 1.00
 */
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final AlohaProperties alohaProperties;

    private final JwtComponent jwtComponent;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        JwtProperties jwt = alohaProperties.getJwt();
        // token cookie名称
        String cookieName = jwt.getCookieName();
        // 获取token值
        String authToken = CookieUtils.getCookieValue(request, cookieName);
        // 忽略拦截的路径
        String[] ignoreUrls = alohaProperties.getSecurity().getIgnoreUrls();
        PathMatcher matcher = new AntPathMatcher();
        // 访问路径是否被忽略拦截
        boolean isIgnore = false;
        for (String ignoreUrl : ignoreUrls) {
            if (matcher.match(ignoreUrl, request.getRequestURI())) {
                isIgnore = true;
                break;
            }
        }
        // 未被忽略，token不为空，有效的token
        if (!isIgnore && StringUtils.isNotBlank(authToken) && jwtComponent.validateToken(authToken)) {
            // 从token中获取用户信息
            UserInfo userInfo = jwtComponent.getInfoFromToken(authToken);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfo, authToken, userInfo.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
