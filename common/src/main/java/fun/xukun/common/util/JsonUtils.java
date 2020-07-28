package fun.xukun.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 日期:2020/3/10
 * Json工具类
 *
 * @author XK
 * @version 1.0
 */
@Slf4j
public final class JsonUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 镀锡序列化成Json字符串
     *
     * @param obj 对象
     * @return Json字符串
     */
    @Nullable
    public static String serialize(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("json序列化异常", e);
            return null;
        }
    }

    /**
     * Json字符串解析成对象
     *
     * @param json Json字符串
     * @param type 类型
     * @param <T>  泛型
     * @return 对象
     */
    @Nullable
    public static <T> T parse(String json, TypeReference<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("json解析异常：" + json, e);
            return null;
        }
    }

    /**
     * Json字符串解析成对象
     *
     * @param json   Json字符串
     * @param tClass 类类型
     * @param <T>    泛型
     * @return 对象
     */
    @Nullable
    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return MAPPER.readValue(json, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("json解析异常：" + json, e);
            return null;
        }
    }

    /**
     * Json字符串解析成集合对象
     *
     * @param json   Json字符串
     * @param eClass 元素类类型
     * @param <E>    泛型
     * @return 集合对象
     */
    @Nullable
    public static <E> List<E> parseList(String json, Class<E> eClass) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("json解析异常：" + json, e);
            return null;
        }
    }

    /**
     * Json字符串转化成映射对象
     *
     * @param json   Json
     * @param kClass 键类类型
     * @param vClass 值类类型
     * @param <K>    泛型
     * @param <V>    泛型
     * @return 映射
     */
    @Nullable
    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("json解析异常：" + json, e);
            return null;
        }
    }
}
