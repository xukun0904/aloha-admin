package fun.xukun.platform.security.config;

import lombok.Data;

/**
 * 日期:2020/6/29
 * Spring Security配置
 *
 * @author xukun
 * @version 1.00
 */
@Data
public class SecurityProperties {

    /**
     * 拦截忽略路径
     */
    private String[] ignoreUrls;

    /**
     * 登录地址
     */
    private String loginPage;
}
