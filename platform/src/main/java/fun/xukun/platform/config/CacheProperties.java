package fun.xukun.platform.config;

import lombok.Data;

/**
 * 日期:2020/7/30
 * Spring Cache配置类
 *
 * @author xukun
 * @version 1.00
 */
@Data
public class CacheProperties {
    /**
     * 缓存时间（单位小时）
     */
    private long expire;

    /**
     * 缓存Key前缀
     */
    private String prefix;
}
