package fun.xukun.platform.config;

import lombok.Data;

/**
 * 日期:2020/6/29
 * 验证码配置类
 *
 * @author xukun
 * @version 1.00
 */
@Data
public class CaptchaProperties {
    /**
     * 验证码有效时间，单位秒
     */
    private long timeout;

    /**
     * 图片宽度，px
     */
    private int width;

    /**
     * 图片高度，px
     */
    private int height;

    /**
     * 验证码位数
     */
    private int len;

    /**
     * 验证码值的类型
     * 1. 数字加字母
     * 2. 纯数字
     * 3. 纯字母
     */
    private int charType;
}
