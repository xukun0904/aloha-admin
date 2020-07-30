package fun.xukun.platform.config;

import fun.xukun.common.model.constant.StringPool;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Objects;

/**
 * 日期:2020/7/22
 * 缓存配置类
 *
 * @author xukun
 * @version 1.00
 */
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {

    private final AlohaProperties properties;

    /**
     * 配置CacheManager
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
        CacheProperties cache = properties.getCache();
        // 基本配置
        RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        // 设置key为String
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getStringSerializer()))
        // 设置value为自动转Json的Object
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
        // 不缓存null
        .disableCachingNullValues()
        // 缓存数据过期时间
        .entryTtl(Duration.ofHours(cache.getExpire()))
        // 设置缓存前缀
        .computePrefixWith(cacheName -> cache.getPrefix() + StringPool.COLON + cacheName + StringPool.COLON);
        // redis缓存管理器
        return RedisCacheManager.RedisCacheManagerBuilder
        // Redis 连接工厂
        .fromConnectionFactory(Objects.requireNonNull(redisTemplate.getConnectionFactory()))
        // 缓存配置
        .cacheDefaults(defaultCacheConfiguration)
        // 配置同步修改或删除put/evict
        .transactionAware()
        .build();
    }

}
