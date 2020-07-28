package fun.xukun.platform.config;

import fun.xukun.platform.security.config.JwtProperties;
import fun.xukun.platform.security.config.SecurityProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 日期:2020/6/29
 * 项目配置类
 *
 * @author xukun
 * @version 1.00
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aloha")
public class AlohaProperties {
    /**
     * 验证码配置
     */
    private CaptchaProperties captcha;

    /**
     * 权限配置
     */
    private SecurityProperties security;

    /**
     * jwt配置
     */
    private JwtProperties jwt;

    /**
     * Spring Cache缓存时间（单位小时）
     */
    private long cacheExpire;

    public AlohaProperties() {
        captcha = new CaptchaProperties();
        security = new SecurityProperties();
        jwt = new JwtProperties();
    }
}
