package fun.xukun.common.util;

import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * 日期:2020/6/8
 * 集合工具类
 *
 * @author xukun
 * @version 1.00
 */
public class CollectionUtils {

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return (collection != null && !collection.isEmpty());
    }
}
