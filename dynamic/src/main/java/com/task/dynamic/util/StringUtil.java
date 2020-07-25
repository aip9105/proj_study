package com.task.dynamic.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    /**
     * 方法说明 : 判断字符串是否为 null 或空字符串，只有空格等视为空字符串
     */
    public static boolean isNull(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 方法说明 : 判断字符串是否为空，null、"null"、"NULL"、"Null"、空格等，都视为空字符串
     *

     * @date 2017/8/3
     */
    public static boolean isNullOrNullValue(String str) {
        return str == null || str.trim().length() == 0 || str.trim().equalsIgnoreCase("null");
    }

    /**
     * 方法说明 : 判断两个字符串是否严格相等，任何字符串为空都视为不等，字符串首尾相差空格等字符也视为不等
     *

     * @date 2017/8/3
     */
    public static boolean equals(String str1, String str2) {
        return str1 != null && str2 != null && str1.equals(str2);
    }

    /**
     * 方法说明 : 判断两个字符串是否相等（trim 后比较），任何字符串为空都视为不等，字符串首尾相差空格等字符视为相等
     *

     * @date 2017/8/3
     */
    public static boolean equalsTrim(String str1, String str2) {
        return str1 != null && str2 != null && str1.trim().equals(str2.trim());
    }

    /**
     * 方法说明 : 判断两个字符串是否相等（trim 后比较），两个空字符串视为相等，"null" 等不做为空字符串看待
     *

     * @date 2017/8/3
     */
    public static boolean equalsNull(String str1, String str2) {
        return !isNull(str1) && !isNull(str2) && str1.trim().equals(str2.trim());
    }

    /**
     * 方法说明 : 判断两个字符串是否相等（trim 后比较），两个空字符串视为相等，"null"、"NULL"、"Null" 等都视为空字符串，也视为相等
     *

     * @date 2017/8/3
     */
    public static boolean equalsNullOrNullValue(String str1, String str2) {
        return !isNullOrNullValue(str1) && !isNullOrNullValue(str2) && str1.trim().equals(str2.trim());
    }

    /**
     * 方法说明: 将字符串转换为指定类型的list集合
     * @param str 要转换的字符串
     * @return 返回转换后的list集合
     */
    public static List<Integer> parseStrToList(String str) {
        //默认逗号分隔
        String[] arr = str.split(",");
        List<Integer> list = new ArrayList<>();
        for (String a : arr) {
            Integer value = Integer.parseInt(a);
            list.add(value);
        }
        return list;
    }

}
