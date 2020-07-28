package fun.xukun.common.util;

/**
 * Created by IntelliJ IDEA.
 * 对象工具类
 *
 * @author XK
 * @version 1.0
 * @date 2018年09月28日
 */
public class ObjectUtils {

    /**
     * 对象转化成字符串
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    /**
     * 对象转化成长整型
     *
     * @param obj 对象
     * @return 长整型
     */
    public static Long toLong(Object obj) {
        if (obj == null) {
            return 0L;
        }
        if (obj instanceof Double || obj instanceof Float) {
            return Long.valueOf(StringUtils.substringBefore(obj.toString(), "."));
        }
        if (obj instanceof Number) {
            return Long.valueOf(obj.toString());
        }
        if (obj instanceof String) {
            return Long.valueOf(obj.toString());
        } else {
            return 0L;
        }
    }

    /**
     * 对象转化成整型
     *
     * @param obj 对象
     * @return 整型
     */
    public static Integer toInt(Object obj) {
        return toLong(obj).intValue();
    }
}