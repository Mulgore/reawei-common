package cn.reawei.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtil {

    /**
     * 电话号码检查
     */
    public static boolean checkIsMobile(String mobile) {
        String regEx = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        Matcher matcher = pattern.matcher(mobile);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }

    /**
     * 邮箱检查
     */
    public static boolean checkIsEmail(String email) {
        String regEx = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 中文检查
     */
    public static boolean checkIsChinese(String item) {
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(item);
        return matcher.matches();
    }

    /**
     * 18位身份证
     */
    public static boolean checkIsIDCard(String item) {
        String regEx = "^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(item);
        return matcher.matches();
    }

    /**
     * URL
     */
    public static boolean checkIsURL(String item) {
        String regEx = "^((http|https)://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(item);
        return matcher.matches();
    }

    /**
     * 邮政编码
     */
    public static boolean checkIsPostalCode(String item) {
        String regEx = "^[1-9]\\d{5}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(item);
        return matcher.matches();
    }

    /**
     * IP
     */
    public static boolean checkIsIP(String item) {
        String regEx = "^(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5])$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(item);
        return matcher.matches();
    }

    /**
     * 国内电话号码
     */
    public static boolean checkIsTelPhone(String item) {
        String regEx = "(\\d{3}-|\\d{4}-)?(\\d{8}|\\d{7})?";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(item);
        return matcher.matches();
    }

    /**
     * 浮点数
     */
    public static boolean checkIsFloatNumber(String item) {
        String regEx = "^(-?\\d+)(\\.\\d+)?$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(item);
        return matcher.matches();
    }

    /**
     * 校验过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     */
    public static boolean checkIsBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     */
    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');  //这边+'0'，不是拼接，在Java和C#中是8+0的ASCII码得到8在ASCII中的编码值，然后通过(char)转成字符'8'
    }

    public static void main(String[] args) {
        System.out.println(RegexUtil.checkIsEmail("qweq@vip.qq.com"));
        System.out.println(RegexUtil.checkIsEmail("875237999@qq.com"));
        System.out.println(RegexUtil.checkIsEmail("875237999@163.com"));
        System.out.println(RegexUtil.checkIsEmail("875237999@aa.com"));
        System.out.println(RegexUtil.checkIsMobile("18649000000"));
        System.out.println(RegexUtil.checkIsMobile("12379202270"));
        System.out.println(RegexUtil.checkIsBankCard("621558220100758999"));
    }
}
