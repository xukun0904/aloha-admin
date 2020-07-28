package fun.xukun.common.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import fun.xukun.common.util.JsonUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * 日期:2020/3/27
 * Redis服务类
 *
 * @author xukun
 * @version 1.00
 */
@Repository
public class RedisRepository {

    private final StringRedisTemplate redisTemplate;

    public RedisRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * set指令
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        if (!(value instanceof String)) {
            value = JsonUtils.serialize(value);
        }
        this.redisTemplate.opsForValue().set(key, (String) value);
    }

    /**
     * set指令，设置生产时间
     *
     * @param key     键
     * @param value   值
     * @param seconds 失效时间
     */
    public void set(String key, Object value, long seconds) {
        if (!(value instanceof String)) {
            value = JsonUtils.serialize(value);
        }
        this.redisTemplate.opsForValue().set(key, (String) value, seconds, TimeUnit.SECONDS);
    }

    /**
     * 执行get指令
     *
     * @param key  键
     * @param type 类型
     * @param <T>  值类型
     * @return 值
     */
    public <T> T get(String key, TypeReference<T> type) {
        String json = this.redisTemplate.opsForValue().get(key);
        if (json == null) {
            return null;
        }
        if (type == null) {
            return (T) json;
        }
        return JsonUtils.parse(json, type);
    }

    /**
     * 执行del指令
     *
     * @param key 建
     */
    public void del(String key) {
        this.redisTemplate.delete(key);
    }

    /**
     * 执行expire指令，设置过期时间
     *
     * @param key     键
     * @param seconds 过期时间
     */
    public void expire(String key, long seconds) {
        this.redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 获取过期时间
     *
     * @param key 键
     * @return 过期时间
     */
    public Long getExpire(String key) {
        return this.redisTemplate.getExpire(key);
    }

    /**
     * 判断键是否存在
     *
     * @param key 建
     * @return 是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
