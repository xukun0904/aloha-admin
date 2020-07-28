package fun.xukun.common.util;


import com.google.common.base.CaseFormat;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import fun.xukun.common.model.constant.StringPool;

import java.util.List;

/**
 * 日期:2020/6/7
 * 字符串工具类
 *
 * @author xukun
 * @version 1.00
 */
public class StringUtils {

    /**
     * 分隔字符串
     *
     * @param string    待分隔的字符串
     * @param separator 分隔符号
     * @return 分隔后的集合
     */
    public static List<String> split(String string, final String separator) {
        return Splitter.on(separator).splitToList(string);
    }

    /**
     * 首字母小写驼峰转小写下划线
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String camelToUnderscore(String value) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, value);
    }

    /**
     * 小写下划线转首字母小写驼峰
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String underscoreToCamel(String value) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value);
    }

    /**
     * 判断字符串是否不为空
     *
     * @param value 字符串
     * @return 是否不为空
     */
    public static boolean isNotBlank(final String value) {
        return !isBlank(value);
    }

    /**
     * 判断字符串是否为空
     *
     * @param value 字符串
     * @return 是否为空
     */
    public static boolean isBlank(String value) {
        return Strings.isNullOrEmpty(value);
    }

    /**
     * 若字符串为空给定默认值
     *
     * @param value        字符串
     * @param defaultValue 默认值
     * @return 不为空的字符串
     */
    public static String defaultIfBlank(final String value, final String defaultValue) {
        return isBlank(value) ? defaultValue : value;
    }

    /**
     * 将字符串转化成小写
     *
     * @param text 字符串
     * @return 转化后的字符串
     */
    public static String lowerCase(final String text) {
        if (StringUtils.isNotBlank(text)) {
            return text.toLowerCase();
        }
        return null;
    }

    /**
     * 忽略大小写判断字符串
     *
     * @param string        对比字符串
     * @param anotherString 对比字符串
     * @return 是否相等
     */
    public static boolean equalsIgnoreCase(String string, String anotherString) {
        if (string == anotherString) {
            return true;
        }
        if (string == null || anotherString == null) {
            return false;
        }
        return string.equalsIgnoreCase(anotherString);
    }

    /**
     * 判断字符串（不忽略大小写）
     *
     * @param string        对比字符串
     * @param anotherString 对比字符串
     * @return 是否相等
     */
    public static boolean equals(String string, String anotherString) {
        if (string == anotherString) {
            return true;
        }
        if (string == null || anotherString == null) {
            return false;
        }
        return string.equals(anotherString);
    }

    /**
     * 截取分隔符之前的字符串
     *
     * @param string    字符串
     * @param separator 分隔符
     * @return 分隔符之前的字符串
     */
    public static String substringBefore(final String string, final String separator) {
        if (isBlank(string) || separator == null) {
            return string;
        }
        if (separator.isEmpty()) {
            return StringPool.EMPTY;
        }
        final int pos = string.indexOf(separator);
        if (pos == -1) {
            return string;
        }
        return string.substring(0, pos);
    }
}
