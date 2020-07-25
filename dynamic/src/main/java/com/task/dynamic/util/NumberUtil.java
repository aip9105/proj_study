package com.task.dynamic.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

    private NumberUtil() {
    }

    /**
     * 方法说明 : 判断是否是10进制数，可判断负数、小数
     *

     * @date 2017/8/3
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$"); // -?[0-9]+.?[0-9]+
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 方法说明 : 判断是否是10进制数，无法判断负数、小数
     *

     * @date 2017/8/3
     */
    public static boolean isNumeric(String str) {
        return StringUtils.isNumeric(str);
    }


    /**
     * 方法说明 : 创建 Integer
     *

     * @date 2017/8/3
     */
    public static Integer createInteger(String str) {
        return (str == null || str.trim().length() == 0) ? null : NumberUtils.createInteger(str);
    }

    /**
     * 方法说明 : 创建 Integer
     *

     * @date 2017/8/3
     */
    public static Integer createInteger(Number n) {
        if (n == null) {
            return null;
        }
        return n.intValue();
    }

    /**
     * 方法说明 : 创建 Integer
     *

     * @date 2017/8/3
     */
    public static Integer createInteger(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return createInteger((Number) o);
        }
        return createInteger(o.toString());
    }

    /**
     * 方法说明 : 创建 Long
     *

     * @date 2017/8/3
     */
    public static Long createLong(String str) {
        return (str == null || str.trim().length() == 0) ? null : NumberUtils.createLong(str);
    }

    /**
     * 方法说明 : 创建 Long
     *

     * @date 2017/8/3
     */
    public static Long createLong(Number n) {
        if (n == null) {
            return null;
        }
        return n.longValue();
    }

    /**
     * 方法说明 : 创建 Long
     *

     * @date 2017/8/3
     */
    public static Long createLong(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return createLong((Number) o);
        }
        return createLong(o.toString());
    }

    /**
     * 方法说明 : 创建 Double
     *

     * @date 2017/8/3
     */
    public static Double createDouble(String str) {
        return (str == null || str.trim().length() == 0) ? null : NumberUtils.createDouble(str);
    }

    /**
     * 方法说明 : 创建 Double
     *

     * @date 2017/8/3
     */
    public static Double createDouble(Number n) {
        if (n == null) {
            return null;
        }
        return n.doubleValue();
    }

    /**
     * 方法说明 : 创建 Double
     *

     * @date 2017/8/3
     */
    public static Double createDouble(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return createDouble((Number) o);
        }
        return createDouble(o.toString());
    }

    /**
     * 方法说明 : 创建 Float
     *

     * @date 2017/8/3
     */
    public static Float createFloat(String str) {
        return (str == null || str.trim().length() == 0) ? null : NumberUtils.createFloat(str);
    }

    /**
     * 方法说明 : 创建 Float
     *

     * @date 2017/8/3
     */
    public static Float createFloat(Number n) {
        if (n == null) {
            return null;
        }
        return n.floatValue();
    }

    /**
     * 方法说明 : 创建 Float
     *

     * @date 2017/8/3
     */
    public static Float createFloat(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return createFloat((Number) o);
        }
        return createFloat(o.toString());
    }

    /**
     * 方法说明 : 创建 BigDecimal
     *

     * @date 2017/8/3
     */
    public static BigDecimal createBigDecimal(String str) {
        return (str == null || str.trim().length() == 0) ? null : NumberUtils.createBigDecimal(str);
    }

    /**
     * 方法说明 : 创建 BigDecimal
     *

     * @date 2017/8/3
     */
    public static BigDecimal createBigDecimal(Object o) {
        if (o == null) {
            return null;
        }
        return createBigDecimal(o.toString());
    }

    /**
     * 方法说明 : 创建 BigInteger
     *

     * @date 2017/8/3
     */
    public static BigInteger createBigInteger(String str) {
        return (str == null || str.trim().length() == 0) ? null : NumberUtils.createBigInteger(str);
    }

    /**
     * 方法说明 : 创建 BigInteger
     *

     * @date 2017/8/3
     */
    public static BigInteger createBigInteger(Object o) {
        if (o == null) {
            return null;
        }
        return createBigInteger(o.toString());
    }

    /**
     * 方法说明 : 比较两个数大小，number1 小于 等于 大于 number2，分别返回 -1 0 1，null 视为无限大
     *

     * @date 2017/8/3
     */
    public static int compare(Number number1, Number number2) {
        if (number1 == null) {
            return number2 == null ? 0 : 1;
        }
        BigDecimal b1 = createBigDecimal(number1);
        BigDecimal b2 = createBigDecimal(number2);
        if (number2 == null) {
            return -1;
        } else {
            if (null == b1) {
                return null == b2 ? 0 : 1;
            } else {
                return b1.compareTo(b2);
            }
        }
    }

    /**
     * 方法说明 : 比较两个数是否相等，都为 null 视为相等
     *

     * @date 2017/8/3
     */
    public static boolean equals(Number number1, Number number2) {
        if (number1 == null) {
            return number2 == null;
        }
        BigDecimal b1 = createBigDecimal(number1);
        BigDecimal b2 = createBigDecimal(number2);
        if (null == b1) {
            return null == b2;
        } else {
            return b1.compareTo(b2) == 0;
        }
    }

}
