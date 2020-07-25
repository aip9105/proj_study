package com.web.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class DateUtil {

    private DateUtil() {
    }


    //日期格式正则表达式
    private static final String DATE_REG
            = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$";
    private static final Pattern PATTERN_DATE = Pattern.compile(DATE_REG);
    //日期格式
    private static final String YYYYMM = "yyyyMM";
    private static final String YYYY_MM = "yyyy-MM";
    private static final String YYYYMMDD = "yyyyMMdd";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 方法说明 : 将 Date 类型时间转换成 String 类型时间
     *
     * @param date   时间
     * @param format 格式

     * @date 2017/8/3
     */
    public static String formatDate(Date date, String format) {
        if(date == null){
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 方法说明 : 将 String 类型时间转换成 Date 类型时间
     *
     * @param date   时间
     * @param format 格式

     * @date 2017/8/3
     */
    public static Date parseDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 方法说明 : 将 Date 类型时间转换成 String 类型时间，格式为yyyy-MM，例：返回2009-10
     *

     * @date 2017/8/3
     */
    public static String getStringYMDate(Date date) {
        return formatDate(date, YYYY_MM);
    }

    /**
     * Date 2017/12/6
     * Author lihao [lihao@sinosoft.com.cn]
     * 方法说明: 将 Date 类型日期转换成  Integer 类型时间,格式为yyyyMM 例如:返回201712
     *
     * @param date 日期
     */
    public static Integer getIntegerYMDate(Date date) {
        return Integer.parseInt(formatDate(date, YYYYMM));
    }

    /**
     * Date 2017/12/6
     * Author lihao [lihao@sinosoft.com.cn]
     * 方法说明: 将 Date 类型日期转换成  Integer 类型时间,格式为yyyyMMdd 例如:返回20171201
     *
     * @param date 日期
     */
    public static Integer getIntegerYMDDate(Date date) {
        return Integer.parseInt(formatDate(date, YYYYMMDD));
    }

    /**
     * 方法说明 : 将 String 类型时间转换成 Date 类型时间，String 格式为 yyyy-MM-dd，例 2017-08-03
     *

     * @date 2017/8/3
     */
    public static Date getDate(String date) {
        if (null == date) {
            return null;
        }
        return parseDate(date, YYYY_MM_DD);
    }

    /**
     * 方法说明 : 将 Integer 类型时间转换成 Date 类型时间，Integer 格式为 yyyyMMdd，例 20170803
     *

     * @date 2017/8/3
     */
    public static Date getDateYMD(Integer date) {
        if (null == date) {
            return null;
        }
        return parseDate(date.toString(), YYYYMMDD);
    }

    /**
     * 方法说明 : 将 Integer 类型时间转换成 Date 类型时间，Integer 格式为 yyyyMM，例 201708
     *

     * @date 2017/8/3
     */
    public static Date getDateYM(Integer date) {
        if (null == date) {
            return null;
        }
        return parseDate(date.toString(), YYYYMM);
    }

    /**
     * 方法说明 : 获取传入时间所在月份的第一天所表示的时间
     *

     * @date 2017/8/3
     */
    public static Date getDateFirst(Date date) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 方法说明 : 获取传入时间所在月份的最后一天所表示的时间
     *

     * @date 2017/8/3
     */
    public static Date getDateLast(Date date) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 方法说明 : 获取传入时间中的指定字段值
     *
     * @param date  时间对象
     * @param field 字段 Calendar.DAY_OF_MONTH 等

     * @date 2017/8/3
     */
    public static Integer getDateField(Date date, int field) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }

    /**
     * 方法说明 : 获取时间中的年份
     *

     * @date 2017/8/3
     */
    public static Integer getYear(Date date) {
        return getDateField(date, Calendar.YEAR);
    }

    /**
     * 方法说明 : 获取时间中的月份
     *

     * @date 2017/8/3
     */
    public static Integer getMonth(Date date) {
        return getDateField(date, Calendar.MONTH);
    }

    /**
     * 方法说明 : 获取时间中的日期
     *

     * @date 2017/8/3
     */
    public static Integer getDay(Date date) {
        return getDateField(date, Calendar.DAY_OF_MONTH);
    }

    /**
     * 方法说明 : 获取下月第一天所表示的时间
     *

     * @date 2017/8/3
     */
    public static Date getNextMonthFirstDate(Date date) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 方法说明 : 获取上月最后一天所表示的时间
     *

     * @date 2017/8/3
     */
    public static Date getLastMonthLastDate(Date date) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取某年最后一天日期
     * @return Date
     */
    public static Date getCurrentYearLast(){
        return getYearLast(LocalDate.now().getYear());
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 方法说明 : 传入时间，指定字段，增加相应值，返回得到的新时间对象
     *
     * @param date  时间对象
     * @param field 字段 Calendar.DAY_OF_MONTH 等
     * @param value 增加值

     * @date 2017/8/3
     */
    public static Date addDateField(Date date, int field, int value) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, value);
        return calendar.getTime();
    }

    /**
     * 方法说明 : 传入时间，增加指定年数，返回得到的新时间对象
     *
     * @param date  时间对象
     * @param years 增加值

     * @date 2017/8/3
     */
    public static Date addYear(Date date, int years) {
        return addDateField(date, Calendar.YEAR, years);
    }

    /**
     * 方法说明 : 传入时间，增加指定月数，返回得到的新时间对象
     *
     * @param date   时间对象
     * @param months 增加值

     * @date 2017/8/3
     */
    public static Date addMonth(Date date, int months) {
        return addDateField(date, Calendar.MONTH, months);
    }

    /**
     * 方法说明 : 传入时间，增加指定天数，返回得到的新时间对象
     *
     * @param date 时间对象
     * @param days 增加值

     * @date 2017/8/3
     */
    public static Date addDay(Date date, int days) {
        return addDateField(date, Calendar.DATE, days);
    }

    /**
     * 方法说明 : 比较两个时间是否相等，允许空值，两者都为空视为相等
     *

     * @date 2017/8/3
     */
    public static boolean equals(Date date1, Date date2) {
        if (date1 == null) {
            return date2 == null;
        }
        return date1.compareTo(date2) == 0;
    }

    /**
     * 方法说明 : 判断 date1 是否在 date2 之后，空视为无穷大
     *

     * @date 2017/8/3
     */
    public static boolean after(Date date1, Date date2) {
        if (date1 == null) {
            return date2 != null;
        }
        return date2 != null && date1.after(date2);
    }

    /**
     * 方法说明 : 判断 date1 是否在 date2 之前，空视为无穷大
     *

     * @date 2017/8/3
     */
    public static boolean before(Date date1, Date date2) {
        if (date1 == null) {
            return false;
        }
        return date2 == null || date1.before(date2);
    }

    /**
     * 方法说明 : 计算两个日期之间的天数，无视时分秒及毫秒
     *
     * @param date1 开始时间，不可为空
     * @param date2 结束时间，不可为空

     * @date 2017/8/3
     */
    public static int calDateSpace(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar1.clear(Calendar.MILLISECOND);
        calendar1.clear(Calendar.SECOND);
        calendar1.clear(Calendar.MINUTE);
        calendar1.clear(Calendar.HOUR_OF_DAY);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        calendar2.clear(Calendar.MILLISECOND);
        calendar2.clear(Calendar.SECOND);
        calendar2.clear(Calendar.MINUTE);
        calendar2.clear(Calendar.HOUR_OF_DAY);
        long time1 = calendar1.getTime().getTime();
        long time2 = calendar2.getTime().getTime();
        return (int) ((time2 - time1) / (24 * 3600 * 1000));
    }

    /**
     * 方法说明 : 计算两个日期之间的月数，不足1个月按0个月计算
     *

     * @date 2017/8/3
     */
    public static int calMonthSpace(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        boolean flip = false; // 是否翻转记录两个日期，如果 date1 晚于 date2，反转记录，最终返回结果乘 -1
        if (date1.after(date2)) {
            calendar1.setTime(date2);
            calendar2.setTime(date1);
            flip = true;
        } else {
            calendar1.setTime(date1);
            calendar2.setTime(date2);
        }
        // 清除时分秒及毫秒，并将日期置为1号
        calendar1.clear(Calendar.MILLISECOND);
        calendar1.clear(Calendar.SECOND);
        calendar1.clear(Calendar.MINUTE);
        calendar1.clear(Calendar.HOUR_OF_DAY);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        calendar2.clear(Calendar.MILLISECOND);
        calendar2.clear(Calendar.SECOND);
        calendar2.clear(Calendar.MINUTE);
        calendar2.clear(Calendar.HOUR_OF_DAY);
        calendar2.set(Calendar.DAY_OF_MONTH, 1);

        int elapsed = 0; // 记录月数
        while (calendar1.before(calendar2)) {
            calendar1.add(Calendar.MONTH, 1);
            elapsed++;
        }
        return flip ? elapsed * -1 : elapsed;
    }

    /**
     * 方法说明 : 判断输入年份是否为闰年
     *

     * @date 2017/8/3
     */
    public static boolean leapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    /**
     * Date 2017/12/11
     * Author lihao [lihao@sinosoft.com.cn]
     * 方法说明: 日期格式化,返回格式: 2017-12-11
     *
     * @param date 日期字符串
     */
    public static Date parseDate(String date) {

        //如果参数为空,则返回null
        if (null == date || date.isEmpty() || date.trim().length() < 8) {
            return null;
        } else {
            date = date.trim();
        }

        //如果excel列单元格格式为日期,此处特殊处理
        if (date.contains("00:00:00.000")) {
            date = date.replaceAll("00:00:00.000", "").trim();
        }

        //返回日期
        Date formatDate = null;
        //如果日期没有/或者-(比如:20180101)
        if (!date.contains("/") && !date.contains("-")) {
            //为了验证日期格式,此处拼接成 2018-01-01的格式
            if (checkValidDate(date)) {
                formatDate = DateUtil.parseDate(date, YYYYMMDD);
            }
        } else {
            //校验日期格式
            if (!validDate(date)) {
                return null;
            }
            //将日期里的 / 替换成 - ,然后进行格式化
            date = date.replaceAll("/", "-");
            formatDate = parseDate(date, YYYY_MM_DD);
        }

        return formatDate;
    }

    /**
     * Date 2018/7/12
     * Author lihao [lihao@sinosoft.com.cn]
     * 方法说明: 正则表达式验证日期格式
     *
     * @param date 日期字符串
     */
    public static Boolean validDate(String date) {
        if (null == date || date.isEmpty()) {
            return Boolean.FALSE;
        }
        return PATTERN_DATE.matcher(date).matches();
    }

    /**
     * Date 2018/7/12
     * Author lihao [lihao@sinosoft.com.cn]
     * 方法说明: 日期格式化,自动补全
     *
     * @param date 日期字符串
     */
    public static Boolean checkValidDate(String date) {
        if (null == date || date.isEmpty()) {
            return Boolean.FALSE;
        } else {
            date = date.trim();
        }
        if (!date.contains("/") && !date.contains("-")) {
            //判断日期长度
            if (date.length() == 6) {
                //默认每月1号
                date = date.substring(0, 4) + "-" + date.substring(4) + "-01";
            } else if (date.length() == 8) {
                date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
            }
        }

        return validDate(date);
    }

    /**
     * 根据出生日期计算年龄
     * @param birthDate
     * @return
     */
    public static int getAgeByBirthDate(Date birthDate) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDate)) {
            throw new IllegalArgumentException("出生日期不能晚于当前时间!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDate);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 将秒数 转化为 时分格式 "93:30”
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        if (time <= 0)
            return "00:00";
        else {
            hour = time/3600;
            //秒数不足1分钟 按一分钟计算
            minute = (int) Math.ceil((double) (time -3600*hour)/60);
            if(hour == 0){
                timeStr = "00:"+unitFormat(minute);
            }else{
                timeStr =  unitFormat(hour)+":"+unitFormat(minute);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        }else {
            retStr = "" + i;
        }
        return retStr;
    }
}
