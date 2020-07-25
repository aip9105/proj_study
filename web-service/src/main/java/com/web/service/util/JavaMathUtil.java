package com.web.service.util;

import java.math.BigDecimal;

/**
 * 描述：由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精确的浮点数运算，包括加减乘除和四舍五入。
 *
 * @author : 单寅轩[shanyinxuan@sinosoft.com.cn]
 * @version : v1.0
 */
public class JavaMathUtil {

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 3;

    // 这个类不能实例化
    private JavaMathUtil() {
    }

    /**
     * 方法说明 : 提供精确的加法运算
     *
     * @param number1 被加数
     * @param number2 加数
     * @return 两个参数的和

     * @date 2017/8/3
     */
    public static BigDecimal add(Number number1, Number number2) {
        BigDecimal bigDecimal1 = NumberUtil.createBigDecimal(number1);
        BigDecimal bigDecimal2 = NumberUtil.createBigDecimal(number2);
        return bigDecimal1.add(bigDecimal2);
    }

    /**
     * 方法说明 : 提供精确的减法运算
     *
     * @param number1 被减数
     * @param number2 减数
     * @return 两个参数的差

     * @date 2017/8/3
     */
    public static BigDecimal subtract(Number number1, Number number2) {
        BigDecimal bigDecimal1 = NumberUtil.createBigDecimal(number1);
        BigDecimal bigDecimal2 = NumberUtil.createBigDecimal(number2);
        return bigDecimal1.subtract(bigDecimal2);
    }

    /**
     * 方法说明 : 提供精确的乘法运算
     *
     * @param number1 被乘数
     * @param number2 乘数
     * @return 两个参数的积

     * @date 2017/8/3
     */
    public static BigDecimal multiply(Number number1, Number number2) {
        BigDecimal bigDecimal1 = NumberUtil.createBigDecimal(number1);
        BigDecimal bigDecimal2 = NumberUtil.createBigDecimal(number2);
        return bigDecimal1.multiply(bigDecimal2);
    }

    /**
     * 方法说明 : 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入
     *
     * @param number1 被除数
     * @param number2 除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商

     * @date 2017/8/3
     */
    public static BigDecimal divide(Number number1, Number number2, int scale) {
        BigDecimal bigDecimal1 = NumberUtil.createBigDecimal(number1);
        BigDecimal bigDecimal2 = NumberUtil.createBigDecimal(number2);
        return bigDecimal1.divide(bigDecimal2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 方法说明 : 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后 DEF_DIV_SCALE 位，以后的数字四舍五入
     *
     * @param number1 被除数
     * @param number2 除数
     * @return 两个参数的商

     * @date 2017/8/3
     */
    public static BigDecimal divide(Number number1, Number number2) {
        return divide(number1, number2, DEF_DIV_SCALE);
    }

    /**
     * 方法说明 : 提供精确的小数位四舍五入处理
     *
     * @param number 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果

     * @date 2017/8/3
     */
    public static BigDecimal round(Number number, int scale) {
        BigDecimal bigDecimal = NumberUtil.createBigDecimal(number);
        return bigDecimal.divide(BigDecimal.ONE, scale, BigDecimal.ROUND_HALF_UP);
    }

}
