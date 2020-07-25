package com.task.dynamic.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：证件号码验证工具类
 * CLASSPATH: IDCardUtil.java
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2018/3/9
 */
@Slf4j
public class IDCardUtil {
    private static String[] ValCodeArr = {"1", "0", "X", "9", "8", "7", "6", "5", "4",
            "3", "2"};
    private static String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
            "9", "10", "5", "8", "4", "2"};

    private static Hashtable<String, Integer> GetTwFirstCode() {
        Hashtable<String, Integer> twFirstCode = new Hashtable<>();
        twFirstCode.put("A", 10);
        twFirstCode.put("B", 11);
        twFirstCode.put("C", 12);
        twFirstCode.put("D", 13);
        twFirstCode.put("E", 14);
        twFirstCode.put("F", 15);
        twFirstCode.put("G", 16);
        twFirstCode.put("H", 17);
        twFirstCode.put("J", 18);
        twFirstCode.put("K", 19);
        twFirstCode.put("L", 20);
        twFirstCode.put("M", 21);
        twFirstCode.put("N", 22);
        twFirstCode.put("P", 23);
        twFirstCode.put("Q", 24);
        twFirstCode.put("R", 25);
        twFirstCode.put("S", 26);
        twFirstCode.put("T", 27);
        twFirstCode.put("U", 28);
        twFirstCode.put("V", 29);
        twFirstCode.put("X", 30);
        twFirstCode.put("Y", 31);
        twFirstCode.put("W", 32);
        twFirstCode.put("Z", 33);
        twFirstCode.put("I", 34);
        twFirstCode.put("O", 35);
        return twFirstCode;
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    private static Hashtable<String, String> GetAreaCode() {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:验证是否为数字
     *
     * @param str 需要验证的字符串
     * @return 是数字则true
     */
    private static boolean isNumeric(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:港澳来往内地通行证
     *
     * @param str 验证的字符串
     * @return 是返回true
     */
    public static boolean vaildHkReToInland(String str) {
        //长度必须是十一位
        if (str.length() != 11) {
            return false;
        } else {
            //开头必须是H或者M
            String s = str.substring(0, 1);
            if (!s.equals("H") && !s.equals("M")) {
                return false;
            } else {
                //后面必须都是数字
                s = str.substring(1, 11);
                return isNumeric(s);
            }
        }
    }

    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:台湾来往内地通行证
     *
     * @param str 验证的字符串
     * @return 是返回true
     */
    public static boolean vaildTwReToInland(String str) {
        //长度必须是11位必须都是数字
        return str.length() == 11 && isNumeric(str);

    }

    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:护照验证
     *
     * @param str 需要验证的字符串
     * @return 如果正确返回true
     */
    public static boolean vaildPassport(String str) {
        return !(str.length() < 8 || str.length() > 10);
    }
    //*********************************** 身份证验证开始 ****************************************
    //身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
    //八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
    //表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位）
    //表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。 4、顺序码（第十五位至十七位）
    //表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数）
    //（1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
    //Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
    //2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0
    //X 9 8 7 6 5 4 3 2


    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:身份证的有效性进行判断 合法情况下，15位自动升为18位
     *
     * @param IDStr 传入的身份证号码
     * @return 失败返回fail，18位且正确right，如果15正确则自动返回生18位
     */
    public static String IDCardValidate(String IDStr) {
        //把含有字母的证件号码里的小写的x转换成大写的X
        IDStr = IDStr.replaceAll("x", "X");
        String Ai = "";
        //号码的长度 15位或18位
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            return "fail";
        }

        //数字 除最后以为都为数字
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (!isNumeric(Ai)) {
            return "fail";
        }

        //出生年月是否有效
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (!isDate(strYear + "-" + strMonth + "-" + strDay)) {
            return "fail";
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                return "fail";
            }
        } catch (NumberFormatException | ParseException e) {
            log.error("身份证有效性验证出错:{}", e);
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            return "fail";
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            return "fail";
        }

        //地区码时候有效
        Hashtable<String, String> h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            return "fail";
        }
        // ==============================================

        /*//判断最后一位的值 */
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (!Ai.equals(IDStr)) {
                return "fail";
            } else {
                return "right";
            }
        } else {
            //返回15位升为18位
            return Ai;
        }
    }


    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:判断字符串是否为日期格式
     *
     * @param strDate 需要验证的日期
     * @return 正确返回true
     */
    private static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        return m.matches();
    }

    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:验证台湾身份证号码
     *
     * @param idCard 需要验证的台湾身份证号码
     * @return 正确返回right否则返回fail
     */
    public static String validateTWCard(String idCard) {
        if (idCard.length() < 9)
            return "fail";
        String start = idCard.substring(0, 1);
        String mid = idCard.substring(1, 9);
        String end = idCard.substring(9, 10);
        Hashtable<String, Integer> h = GetTwFirstCode();
        Integer iStart = h.get(start);
        //开始不是字母
        if (iStart == null)
            return "fail";
        Integer sum = iStart / 10 + (iStart % 10) * 9;
        char[] chars = mid.toCharArray();
        Integer iflag = 8;
        for (char c : chars) {
            sum = sum + Integer.valueOf(c + "") * iflag;
            iflag--;
        }
        return (sum % 10 == 0 ? 0 : (10 - sum % 10)) == Integer.valueOf(end) ? "right" : "fail";
    }

    /**
     * 验证香港澳门
     * 验证香港身份证号码(存在Bug，部份特殊身份证无法检查)
     * <p>
     * 身份证前2位为英文字符，如果只出现一个英文字符则表示第一位是空格，对应数字58 前2位英文字符A-Z分别对应数字10-35
     * 最后一位校验码为0-9的数字加上字符"A"，"A"代表10
     * </p>
     * <p>
     * 将身份证号码全部转换为数字，分别对应乘9-1相加的总和，整除11则证件号码有效
     * </p>
     *
     * @param idCard 身份证号码
     * @return 验证码是否符合正确返回right错误返回fail
     */
    public static String validateHKCard(String idCard) {
        if (idCard.length() < 7)
            return "fail";
        String card = idCard.replaceAll("[\\(|\\)]", "");
        Integer sum;
        if (card.length() == 9) {
            sum = ((int) card.substring(0, 1).toUpperCase().toCharArray()[0] - 55) * 9
                    + ((int) card.substring(1, 2).toUpperCase().toCharArray()[0] - 55) * 8;
            card = card.substring(1, 9);
        } else {
            sum = 522 + ((int) card.substring(0, 1).toUpperCase().toCharArray()[0] - 55) * 8;
        }
        String mid = card.substring(1, 7);
        String end = card.substring(7, 8);
        char[] chars = mid.toCharArray();
        Integer iflag = 7;
        for (char c : chars) {
            sum = sum + Integer.valueOf(c + "") * iflag;
            iflag--;
        }
        if (end.toUpperCase().equals("A")) {
            sum = sum + 10;
        } else {
            sum = sum + Integer.valueOf(end);
        }
        return (sum % 11 == 0) ? "right" : "fail";
    }


    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:
     * 香港  身份证：
     * 身份证号码是：C668668（9）。
     * 香港身份证号码由三部分组成：一个英文字母；6个数字；括号及0-9中的任一个数字，或者字母A。括号中的数字或字母A，是校验码，用于检验括号前面的号码的逻辑正确性。
     * 逻辑关系：
     * 先把首位字母改为数字，即A为1，B为2，C为3...Z为26，再乘以8；然后把字母后面的6个数字依次乘以7、6、5、4、3、2；再将以上所有乘积相加的和，除以11，得到余数；如果整除，则括号中的校验码为0，如果余数为1，则校验码为A，如果余数为2～10，则用11减去这个余数的差作校验码。
     * 例如：P103265（1），P，在字母表中排行16，则以16代表，则计算校验码：
     * 16×8＋1×7＋0×6＋3×5＋2×4＋6×3＋5×2＝186
     * 186÷11＝16......余10
     * 11－10＝1，即校验码为1。
     *
     * @param idCard 香港身份证
     * @return 正确返回right否则返回fail
     */
    private static String HKIDCardvalidate(String idCard) {

        String card = idCard.replaceAll("[\\(|\\)]", "");
        if (card.length() != 8) {
            return "fail";
        } else {
            if (idCard.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$")) {
                return "right";
            }
        }
        return null;
    }

    /**
     * 澳门身份证：
     * A 持证人于澳门出生
     * B 持证人于香港出生
     * C 持证人于中国大陆、台湾出生
     * D 持证人于其他国家及地区出生
     * N 持证人出生地不明，不知道自己在何处出生
     * -------------
     * S 持证人有出生证明文件。如无出生证明文件则会漏空。
     * M 持证人为男性（Masculino）
     * F 持证人为女性（Feminino）
     * 2楼
     * 澳门身份证号码由8个拉丁数字组成，格式为：XNNNNNN(Y)。其中：
     * 第一位X，可能是1、5、7。绝大多数人以1字开首；以5字开首的身份证号码代表持有或曾经持有葡萄牙国民身份证或葡萄牙给外国人身份证之人士；以7字开首代表曾经取得蓝卡之人士，大多都是在1970年代至1980年代期间从中国大陆持合法证件到澳门的人士。
     * 最后一位Y，是查核用数字，是为方便电脑处理资料及检查号码输入的正确性而设。
     * 中间6位数字，是发证当局给出的顺序号。
     */
    public static String MacaoIDCardvalidate(String idCard) {

        String card = idCard.replaceAll("[\\(|\\)]", "");
        if (card.length() != 8) {
            return "fail";
        } else {
            if (idCard.matches("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?$")) {
                return "right";
            }
        }
        return null;
    }

    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:
     * * 台湾人的身份证号码：
     * 共十码
     * 第1码是英文字母，不同的县巿用不同的字母
     * 每个字母代表两个数字
     * 第2码代表性别(1是男生; 2是女生)
     * 第3码~第9码是顺序码(基本上是越早出生的号码越前面)
     * 第10码是判别码(判别是否为合法的身份证号码)
     * 把前9码所代表的数字列出来，再乘以他的加权数(我记得是10~1)
     * 接下来全部加起来，然后总和以10去除，取余数之后
     * 以10来减那个余数之后得到的那个数字再以10去除取余数之后那个数字就是最后一个判别码
     * 所以最简单的方法是前面9个码依规则乱编，最后一码从0试到9，一定有一个会是对的。
     *
     * @param idCard 台湾身份证
     * @return 正确返回right错误返回fail
     */
    private static String TWIDCardvalidate(String idCard) {


        if (idCard.length() != 10) {
            return "fail";
        } else if (idCard.matches("^[a-zA-Z][0-9]{9}$")) {
            return "right";
        } else {
            return "fail";
        }

    }

    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:中国护照号一共是8或9位数。开头一个大写字母，因私护照后面是7个阿拉伯数字，因公护照后面是8个阿拉伯数字。
     *
     * @param number 中国护照号码
     * @return 正确返回right错误返回fail
     */
    public static String ChinaPassportNumber(String number) {
        if (number.length() == 8 || number.length() == 9) {
            /*
             * 因私普通护照号码格式有:14/15+7位数,G+8位数；
    		 * 因公普通的是:P.+7位数；
				公务的是：S.+7位数 或者 S+8位数,以D开头的是外交护照.D
    		 */
            if (number.matches("^1[45][0-9]{7}|G[0-9]{8}|P.[0-9]{7}|S.[0-9]{7}|S[0-9]{8}|.D[0-9]+$")) {
                //"^[A-Z]{1}[0-9]{7,8}$"
                return "right";
            }
        }

        return "fail";
    }

    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:外国护照，一般九个字符
     *
     * @param number 外国护照号码
     * @return 正确返回right错误返回fail
     */
    public static String foreignPassportNumber(String number) {
        if (number.length() < 8 || number.length() > 10) {//一般为9位数字字母混合排列
            return "fail";

        } else if (number.matches("^[a-zA-Z0-9]{9}$")) {
            return "right";

        } else {
            return "fail";

        }

    }

    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:士兵证
     * 士兵证号码为七位数，从左至右前两位数为军级单位编号，其余五位数为士兵证序号，由各大单位自行编排。号码前要冠以各大单位“冠字”头，如沈阳军区应为“沈字第0100000号”。
     *
     * @param card 士兵证号码
     * @return 正确返回right错误返回fail
     */
    public static String SolderCard(String card) {
        //士兵证和 警官证值传入数字
        if (card.matches("^[\u4e00-\u9fa5]{0,5}[a-zA-Z0-9]{7}[\u4e00-\u9fa5]{1}$")) {
            //不需要中文去掉 [\u4e00-\u9fa5]{0,5} 即可
            return "right";
        }

        return "fail";
    }


    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:武警警官证
     * 、警官证都是用大单位的缩略加上7位数字，比如总参的军官就是参0000000，广州军区就会是广1234567，等等
     *
     * @param card 武警证号码
     * @return 正确返回right错误返回fail
     */
    public static String PoliceCard(String card) {
        if (card.matches("^[\u4e00-\u9fa5]{0,5}[a-zA-Z0-9]{7}$")) {
            return "right";
        }
        return "fail";
    }


    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:
     * 军官证的编号数字是8位。数字前边是你所在大军区的级别的单位简称有 南 北 沈 兰 成 济 广  参 证 后 装 海 空
     * 比如我的就是  南字**070509
     *
     * @param str 军官证号码
     * @return 正确返回true错误返回fail
     */
    public static boolean vaildOfficer(String str) {
        return str.matches("^[\u4e00-\u9fa5]{0,5}[a-zA-Z0-9]{8}$");
    }

    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:
     * 港澳台身份证验证
     * HK：XYYYYYY(Z)  macao:XYYYYYY(Z)  taiwan:共十位，一位字母九位数字
     *
     * @param idCard 港澳台身份证
     * @return 正确返回right错误返回fail
     */
    public static String HKMACAOTWValidate(String idCard) {
        if (TWIDCardvalidate(idCard).equals("right")) {
            return "right";
        } else if ("right".equals(HKIDCardvalidate(idCard))) {
            return "right";
        } else if (("right").equals(MacaoIDCardvalidate(idCard))) {
            return "right";
        } else {
            return "fail";
        }
    }

    /**
     * Date 2017/5/2
     * Author 张政[zhang_zheng@sinosoft.com.cn]
     * <p>
     * 方法说明:将姓名的全角转换为半角 并且 首尾空格去掉，中间有多个空格只保留一个
     *
     * @param name 需要转换的姓名
     * @return 返回转换后的姓名
     */
    public static String fullWidth2halfWidth(String name) {
        char[] charArray = name.trim().trim().replaceAll(" ", "").toCharArray();
        // 对全角字符转换的char数组遍历
        for (int i = 0; i < charArray.length; ++i) {
            int charIntValue = (int) charArray[i];
            // 如果符合转换关系,将对应下标之间减掉偏移量65248;如果是空格的话,直接做转换
            if (charIntValue >= 65281 && charIntValue <= 65374) {
                charArray[i] = (char) (charIntValue - 65248);
            } else if (charIntValue == 12288) {
                charArray[i] = (char) 32;
            }
        }
        return new String(charArray);
    }

    //public static void main(String[] args) throws ParseException {
    //    System.out.println(VildIDCard.IDCardValidate("230521921112172"));
    //    System.out.println(HKIDCardvalidate("C668668(9)"));
    //    System.out.println(ChinaPassportNumber("D12345678"));
    //    System.out.println(SolderCard("沈字第0100000号"));
    //    System.out.println(ChinaPassportNumber("G43452678"));
    //    System.out.println(vaildOfficer("沈00100000"));
    //}
}
