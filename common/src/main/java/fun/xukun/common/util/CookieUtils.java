package fun.xukun.common.util;

import fun.xukun.common.model.constant.StringPool;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 日期:2020/3/27
 * Cookie工具类
 *
 * @author xukun
 * @version 1.00
 */
@Slf4j
public final class CookieUtils {

    private static final String PATTERN_IP = "(\\d*\\.){3}\\d*";

    /**
     * 得到Cookie的值, 不编码
     *
     * @param request    请求
     * @param cookieName cookie名
     * @return 对应cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }

    /**
     * 得到Cookie的值,
     *
     * @param request    请求
     * @param cookieName cookie名
     * @param isDecoder  是否解码
     * @return 对应cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (Cookie aCookieList : cookieList) {
                if (aCookieList.getName().equals(cookieName)) {
                    if (isDecoder) {
                        retValue = URLDecoder.decode(aCookieList.getValue(), StringPool.UTF_8);
                    } else {
                        retValue = aCookieList.getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error("Cookie Decode Error.", e);
        }
        return retValue;
    }

    /**
     * 得到Cookie的值,
     *
     * @param request      请求
     * @param cookieName   cookie名
     * @param encodeString 解码格式
     * @return 对应cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (Cookie aCookieList : cookieList) {
                if (aCookieList.getName().equals(cookieName)) {
                    retValue = URLDecoder.decode(aCookieList.getValue(), encodeString);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error("Cookie Decode Error.", e);
        }
        return retValue;
    }

    /**
     * 生成cookie，并指定编码
     *
     * @param request      请求
     * @param response     响应
     * @param cookieName   name
     * @param cookieValue  value
     * @param encodeString 编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, String encodeString) {
        setCookie(request, response, cookieName, cookieValue, null, encodeString, null);
    }

    /**
     * 生成cookie，并指定生存时间
     *
     * @param request      请求
     * @param response     响应
     * @param cookieName   name
     * @param cookieValue  value
     * @param cookieMaxAge 生存时间
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, Integer cookieMaxAge) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxAge, null, null);
    }

    /**
     * 设置cookie，不指定httpOnly属性
     *
     * @param request      请求
     * @param response     响应
     * @param cookieName   name
     * @param cookieValue  value
     * @param cookieMaxAge 生存时间
     * @param encodeString 解码格式
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, Integer cookieMaxAge, String encodeString) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxAge, encodeString, null);
    }

    /**
     * 设置cookie，不指定编码
     *
     * @param request      请求
     * @param response     响应
     * @param cookieName   name
     * @param cookieValue  value
     * @param cookieMaxAge 生存时间
     * @param httpOnly     是否指定httpOnly属性
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, Integer cookieMaxAge, Boolean httpOnly) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxAge, null, httpOnly);
    }

    /**
     * 设置Cookie的值，并使其在指定时间内生效
     *
     * @param request      请求
     * @param response     响应
     * @param cookieName   name
     * @param cookieValue  value
     * @param cookieMaxAge 生存时间
     * @param encodeString 解码格式
     * @param httpOnly     是否指定httpOnly属性
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, Integer cookieMaxAge, String encodeString, Boolean httpOnly) {
        try {
            if (StringUtils.isBlank(encodeString)) {
                encodeString = StringPool.UTF_8;
            }

            if (cookieValue == null) {
                cookieValue = StringPool.EMPTY;
            } else {
                cookieValue = URLEncoder.encode(cookieValue, encodeString);
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxAge != null && cookieMaxAge > 0) {
                cookie.setMaxAge(cookieMaxAge);
            }
            // 设置域名的cookie
            if (null != request) {
                cookie.setDomain(getDomainName(request));
            }
            cookie.setPath(StringPool.SLASH);
            if (httpOnly != null) {
                cookie.setHttpOnly(httpOnly);
            }
            response.addCookie(cookie);
        } catch (Exception e) {
            log.error("Cookie Encode Error.", e);
        }
    }

    /**
     * 得到cookie的域名
     *
     * @param request 请求
     * @return 域名
     */
    public static String getDomainName(HttpServletRequest request) {
        String domainName;
        String serverName = request.getRequestURL().toString();
        if (StringPool.EMPTY.equals(serverName)) {
            domainName = StringPool.EMPTY;
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            final int end = serverName.indexOf(StringPool.SLASH);
            serverName = serverName.substring(0, end);
            if (serverName.indexOf(StringPool.COLON) > 0) {
                String[] ary = serverName.split(StringPool.COLON);
                serverName = ary[0];
            }
            if (serverName.matches(PATTERN_IP)) {
                domainName = serverName;
            } else {
                final String[] domains = serverName.split("\\.");
                int len = domains.length;
                if (len > 3) {
                    // www.xxx.com.cn
                    domainName = domains[len - 3] + StringPool.DOT + domains[len - 2] + StringPool.DOT + domains[len - 1];
                } else if (len > 1) {
                    // xxx.com or xxx.cn
                    domainName = domains[len - 2] + StringPool.DOT + domains[len - 1];
                } else {
                    domainName = serverName;
                }
            }
        }
        return domainName;
    }

}
