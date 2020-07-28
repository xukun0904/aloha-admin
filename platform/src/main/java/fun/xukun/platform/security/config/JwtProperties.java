package fun.xukun.platform.security.config;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * 说明: jwt授权属性
 *
 * @author XK
 * @version 1.0
 * @date 2019年03月28日
 */
@Data
public class JwtProperties {
    /**
     * 登录校验的密钥
     */
    private String secret;

    /**
     * 过期时间,单位分钟
     */
    private Integer expire;

    /**
     * cookie名
     */
    private String cookieName;

    /**
     * cookie最大存活时间
     */
    private Integer cookieMaxAge;

    /**
     * header名称
     */
    private String header;

    /**
     * 令牌前缀
     */
    private String tokenStartWith;
}
