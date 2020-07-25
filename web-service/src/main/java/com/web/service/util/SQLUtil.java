package com.web.service.util;

import java.util.*;

public class SQLUtil {

    /**
     * 方法说明 : 处理oracle SQL 语句in子句中超过1000项就会报错的问题。 如 where field in (...)，可以改为 where 后面跟调用方法得到字符串，避免or条件的逻辑错误
     *
     * @param idStr in语句中的原参数字符串
     * @param regex 字符串分割符
     * @param count 限制in语句中出现的条件个数
     * @param field in语句对应的数据库查询字段
     * @return 返回 (field in (...) or field in (...)) 字符串

     * @date 2017/8/3
     */
    public static String getOracleSQLIn(String idStr, String regex, int count, String field) {
        // 将 in 语句中参数字符串转为集合
        String[] idArray = idStr.split(regex);
        List<String> ids = new ArrayList<>();
        ids.addAll(Arrays.asList(idArray));

        // Oracle 中 in 条件参数个数限制为1000，此处取和1000比的最小值，作为每个 in 条件中的参数个数
        count = Math.min(count, 1000);
        int len = ids.size(); // 参数总个数
        // 判断需要几个 in 条件
        int size = len % count;
        if (size == 0) {
            size = len / count;
        } else {
            size = (len / count) + 1;
        }

        StringBuilder inSql = new StringBuilder();
        inSql.append("(");
        for (int i = 0; i < size; i++) {
            int fromIndex = i * count;
            int toIndex = Math.min(fromIndex + count, len);

            // 将集合中部分元素连接为 1','2','3','4','5 的形式，如果为空，则返回空字符串
            String productId = org.apache.commons.lang3.StringUtils.defaultIfEmpty(org.apache.commons.lang3.StringUtils.join(ids.subList(fromIndex, toIndex), "','"), "");
            if (i != 0) {
                inSql.append(" or ");
            }
            inSql.append(field).append(" in ('").append(productId).append("')");
        }
        inSql.append(")");

        return inSql.toString();
    }

    /**
     * 方法说明 : 处理oracle SQL 语句in子句中超过1000项就会报错的问题。 如 where field in (...)，可以改为 where 后面跟调用方法得到字符串，避免or条件的逻辑错误
     *
     * @param collection in语句中的原参数集合，注意集合中的元素会调用String的valueOf方法转换为字符串
     * @param count 限制in语句中出现的条件个数
     * @param field in语句对应的数据库查询字段
     * @return 返回 (field in (...) or field in (...)) 字符串

     * @date 2017/8/3
     */
    public static String getOracleSQLIn(Collection<?> collection, String regex, int count, String field) {
        String idStr = org.apache.commons.lang3.StringUtils.join(collection, ",");
        return getOracleSQLIn(idStr, ",", count, field);
    }

    /**
     * 方法说明 : 处理oracle SQL 语句in子句中超过1000项就会报错的问题。 如 where field in (...)，可以改为 where 后面跟调用方法得到字符串，避免or条件的逻辑错误
     *
     * @param iterator in语句中的原参数集合的迭代器，注意迭代器中的元素会调用String的valueOf方法转换为字符串
     * @param count 限制in语句中出现的条件个数
     * @param field in语句对应的数据库查询字段
     * @return 返回 (field in (...) or field in (...)) 字符串

     * @date 2017/8/3
     */
    public static String getOracleSQLIn(Iterator<?> iterator, int count, String field) {
        String idStr = org.apache.commons.lang3.StringUtils.join(iterator, ",");
        return getOracleSQLIn(idStr, ",", count, field);
    }

    /**
     * 方法说明 : 处理oracle SQL 语句in子句中超过1000项就会报错的问题。 如 where field in (...)，可以改为 where 后面跟调用方法得到字符串，避免or条件的逻辑错误
     *
     * @param arrs in语句中的原参数数组，注意数组中的元素会调用String的valueOf方法转换为字符串
     * @param count 限制in语句中出现的条件个数
     * @param field in语句对应的数据库查询字段
     * @return 返回 (field in (...) or field in (...)) 字符串

     * @date 2017/8/3
     */
    public static String getOracleSQLIn(Object[] arrs, int count, String field) {
        String idStr = org.apache.commons.lang3.StringUtils.join(arrs, ",");
        return getOracleSQLIn(idStr, ",", count, field);
    }

    /**
     * 方法说明 : 解决oracle存储过程4000长度限制问题
     *
     * @param idStr 传入的参数串
     * @param regex 字符串分割符
     * @param count 限制参数的个数
     * @return 返回拆分的字符串集合 { "1,2,3", "4,5,6", "7,8" } 的形式

     * @date 2017/8/3
     */
    public static List<String> getOracleProceSQLList(String idStr, String regex, int count) {
        if (idStr == null || idStr.trim().length() == 0) {
            return new ArrayList<>(0);
        }

        // 将字符串转为集合
        String[] idArray = idStr.split(regex);
        List<String> ids = new ArrayList<String>();
        int maxLenth = 0; // 参数的最大长度+1（要算上逗号）
        for (String string : idArray) {
            ids.add(string);
            maxLenth = Math.max(maxLenth, string.length() + 1);
        }

        // 把总长度限制在3000内
        while (maxLenth * count > 3000) {
            count -= 1;
        }

        // Oracle 中 in 条件参数个数限制为1000，此处取和1000比的最小值
        count = Math.min(count, 1000);
        int len = ids.size(); // 参数总个数
        // 判断需要拆分成几个
        int size = len % count;
        if (size == 0) {
            size = len / count;
        } else {
            size = (len / count) + 1;
        }
        List<String> idList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int fromIndex = i * count;
            int toIndex = Math.min(fromIndex + count, len);

            // 将集合中部分元素连接为 1,2,3,4,5 的形式，如果为空，则返回空字符串
            String oraIdStr = org.apache.commons.lang3.StringUtils.defaultIfEmpty(org.apache.commons.lang3.StringUtils.join(ids.subList(fromIndex, toIndex), ","), "");
            idList.add(oraIdStr);
        }

        return idList;
    }

    /**
     * 方法说明 : 解决oracle存储过程4000长度限制问题
     *
     * @param collection 传入的参数集合，注意集合中的元素会调用String的valueOf方法转换为字符串
     * @param count 限制参数的个数
     * @return 返回拆分的字符串集合 { "1,2,3", "4,5,6", "7,8" } 的形式

     * @date 2017/8/3
     */
    public static List<String> getOracleProceSQLList(Collection<?> collection, int count) {
        if (collection == null || collection.size() == 0) {
            return new ArrayList<>(0);
        }

        String idStr = org.apache.commons.lang3.StringUtils.join(collection, ",");
        return getOracleProceSQLList(idStr, ",", count);
    }

    /**
     * 方法说明 : 解决oracle存储过程4000长度限制问题
     *
     * @param iterator 传入的参数集合的迭代器，注意迭代器中的元素会调用String的valueOf方法转换为字符串
     * @param count 限制参数的个数
     * @return 返回拆分的字符串集合 { "1,2,3", "4,5,6", "7,8" } 的形式

     * @date 2017/8/3
     */
    public static List<String> getOracleProceSQLList(Iterator<?> iterator, int count) {
        if (iterator == null || !iterator.hasNext()) {
            return new ArrayList<>(0);
        }

        String idStr = org.apache.commons.lang3.StringUtils.join(iterator, ",");
        return getOracleProceSQLList(idStr, ",", count);
    }

    /**
     * 方法说明 : 解决oracle存储过程4000长度限制问题
     *
     * @param arrs 传入的参数数组，注意数组中的元素会调用String的valueOf方法转换为字符串
     * @param count 限制参数的个数
     * @return 返回拆分的字符串集合 { "1,2,3", "4,5,6", "7,8" } 的形式

     * @date 2017/8/3
     */
    public static List<String> getOracleProceSQLList(Object[] arrs, int count) {
        if (arrs == null || arrs.length == 0) {
            return new ArrayList<>(0);
        }

        String idStr = org.apache.commons.lang3.StringUtils.join(arrs, ",");
        return getOracleProceSQLList(idStr, ",", count);
    }

}
