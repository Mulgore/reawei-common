package cn.reawei.common.utils;

/**
 * 字符串替换
 */
public class StringReplaceUtil {
    private static StringBuilder builder;

    /**
     * 银行卡替换***
     */
    public static String bankCard(String item) {
        builder = new StringBuilder();
        builder.append(item.substring(0, 4));
        builder.append("******");
        builder.append(item.substring(item.length() - 4, item.length()));
        return builder.toString();
    }

    /**
     * 电话号码替换***
     */
    public static String mobile(String item) {
        builder = new StringBuilder();
        builder.append(item.substring(0, 3));
        builder.append("****");
        builder.append(item.substring(7, item.length()));
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(bankCard("123456789011121314"));
        System.out.println(mobile("18649000000"));
    }

}
