package fun.xukun.platform.security.service;

import fun.xukun.platform.security.model.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

/**
 * 日期:2020/6/28
 * 鉴权服务类
 *
 * @author xukun
 * @version 1.00
 */
public interface AuthService extends UserDetailsService {
    /**
     * 获取当前用户
     *
     * @param request 请求
     * @return 当前用户
     */
    UserInfo getCurrentUserInfo(HttpServletRequest request);
}
