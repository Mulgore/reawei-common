package cn.reawei.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
    static Calendar calendar;
    protected static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static String DateToStr(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Date 转 String 类型失败: " + e);
            }
        }
        return null;

    }

    public static String DateToStr(Date date) {
        return DateToStr(date, "yyyy-MM-dd");
    }

    public static String dateToStr(Date date) {
        return DateToStr(date, "yyyyMMdd");
    }

    public static String DateTimeToStr(Date date) {
        return DateToStr(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String DateTimeToStrBySSS(Date date) {
        return DateToStr(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static String dateTimeToStr(Date date) {
        return DateToStr(date, "yyyyMMddHHmmss");
    }

    public static String dateTimeToStrs(Date date) {
        return DateToStr(date, "yyyyMMddHHmmssSSS");
    }

    public static String dateTimeToTStr(Date date) {
        return DateToStr(date, "yyyy-MM-dd'T'HH:mm:ss");
    }

    public static String TimeToStr(Date date) {
        return DateToStr(date, "HH:mm:ss");
    }

    public static String timeToStr(Date date) {
        return DateToStr(date, "HHmmss");
    }

    public static String YearToStr(Date date) {
        return DateToStr(date, "yyyy");
    }

    public static int YearToInt(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(1);
    }

    public static String MonthToStr(Date date) {
        return DateToStr(date, "MM");
    }

    public static int MonthToInt(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(2) + 1;
    }

    public static String DayToStr(Date date) {
        return DateToStr(date, "dd");
    }

    public static int DayToInt(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(5);
    }

    public static String YearMonthToStr(Date date) {
        return DateToStr(date, "yyyy-MM");
    }

    public static String yearMonthToStr(Date date) {
        return DateToStr(date, "yyyyMM");
    }

    public static String MonthDayToStr(Date date) {
        return DateToStr(date, "MM-dd");
    }

    public static String monthDayToStr(Date date) {
        return DateToStr(date, "MMdd");
    }

    public static String DayMonthYearToStr(Date date) {
        return DateToStr(date, "dd/MM/yyyy");
    }

    public static String YearMonthDayToStr(Date date) {
        return DateToStr(date, "yyyy/MM/dd");
    }

    public static Date StrToDate(String sDate, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(sDate);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("String 转 Date 类型失败: " + e);
            }
        }
        return null;
    }

    public static Date StrToDate(String sDate) {
        return StrToDate(sDate, "yyyy-MM-dd");
    }

    public static Date strToDate(String sDate) {
        return StrToDate(sDate, "yyyyMMdd");
    }

    public static Date SDateTimeToDate(String sDateTime) {
        return StrToDate(sDateTime, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date SDateToDate(String sDate) {
        return StrToDate(sDate, "yyyy-MM-dd");
    }

    public static Date SDateTimeToDateBySSS(String sDateTime) {
        return StrToDate(sDateTime, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static Date sDateTimeToDate(String sDateTime) {
        return StrToDate(sDateTime, "yyyyMMddHHmmss");
    }

    public static Date addDate(Date date, int val) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.DATE, val);
        return gc.getTime();
    }

    public static Date addHour(Date date, int val) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.HOUR, val);
        return gc.getTime();
    }

    public static Date subDate(Date date, int val) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(5, -val);
        return gc.getTime();
    }

    public static Date subHour(Date date, int val) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(10, -val);
        return gc.getTime();
    }

    public static Date subMinute(Date date, int val) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(12, -val);
        return gc.getTime();
    }

    public static Date addMonth(Date date, int val) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(2, val);
        return gc.getTime();
    }

    public static Date addMinute(Date date, int val) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(12, val);
        return gc.getTime();
    }

    public static Date addSecond(Date date, int val) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.SECOND, val);
        return gc.getTime();
    }

    public static Date addYear(Date date, int val) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(1, val);
        return gc.getTime();
    }

    public static int getDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(6);
    }

    public static int sumDayByYearMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(1, year);
        c.set(2, month - 1);
        return c.getActualMaximum(5);
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(5, c.getActualMaximum(5));
        return c.getTime();
    }

    public static Date getLastDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(1, year);
        c.set(2, month - 1);
        c.set(5, c.getActualMaximum(5));
        return c.getTime();
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(5, c.getActualMinimum(5));
        return c.getTime();
    }

    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(1, year);
        c.set(2, month - 1);
        c.set(5, c.getActualMinimum(5));
        return c.getTime();
    }

    public static List<Date> getDateList(Date startDate, Date endDate) {
        Date tempDate = startDate;
        List<Date> dateList = new ArrayList<Date>();
        if (isSameDay(startDate, endDate)) {
            dateList.add(tempDate);
        } else {
            while (tempDate.before(endDate)) {
                dateList.add(tempDate);
                tempDate = DateUtils.addDays(tempDate, 1);
            }
            dateList.add(tempDate);
        }
        return dateList;
    }

    public static List<String> getDateToStrList(String startDate, String endDate, String format) {
        return getDateList(startDate, endDate, format);
    }

    public static List<String> getDateToStrList(String startDate, String endDate) {
        return getDateList(startDate, endDate, "yyyy-MM-dd");
    }

    private static List<String> getDateList(String startDate, String endDate, String format) {
        List<String> sDateList = new ArrayList<>();
        Date periodDate = StrToDate(startDate, format);
        if (startDate.equals(endDate)) {
            sDateList.add(DateToStr(periodDate, format));
        } else {
            while (periodDate.before(StrToDate(endDate, format))) {
                sDateList.add(DateToStr(periodDate, format));
                periodDate = DateUtils.addDays(periodDate, 1);
            }
            sDateList.add(DateToStr(periodDate, format));
        }
        return sDateList;
    }

    public static List<Date> getYearMonthAllDate(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        List<Date> dateList = new ArrayList<>();
        int size = c.getActualMaximum(5);
        for (int i = 0; i < size; i++) {
            c.set(5, i + 1);
            dateList.add(c.getTime());
        }
        return dateList;
    }

    public boolean isLeapYear(int year) {
        GregorianCalendar gc = new GregorianCalendar();
        return gc.isLeapYear(year);
    }

    public boolean isLeapYear(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        return gc.isLeapYear(YearToInt(date));
    }

    public static int getDays(Date sd, Date ed) {
        return (int) ((ed.getTime() - sd.getTime()) / 86400000L);
    }

    public static Calendar mergeDateTime(Date date, Time time) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        if (time != null) {
            Calendar temp = Calendar.getInstance();
            temp.setTime(time);
            cal.set(Calendar.HOUR_OF_DAY, temp.get(Calendar.HOUR_OF_DAY));
            cal.set(Calendar.MINUTE, temp.get(Calendar.MINUTE));
            cal.set(Calendar.SECOND, temp.get(Calendar.SECOND));
            cal.set(Calendar.MILLISECOND, temp.get(Calendar.MILLISECOND));
        }
        return cal;
    }

    public static int diff_in_date(Date d1, Date d2) {
        return (int) (d1.getTime() - d2.getTime()) / 86400000;
    }

    /**
     * 两个时间点相差多少秒
     *
     * @param d1 开始时间
     * @param d2 结束时间
     * @return 时间差
     */
    public static int getDifferMinutes(Date d1, Date d2) {
        return (int) (d2.getTime() - d1.getTime()) / 1000;
    }

    public static Calendar getDateBegin(int year, int month, int date) {
        Calendar begin_time = Calendar.getInstance();
        begin_time.set(Calendar.YEAR, year);
        begin_time.set(Calendar.MONTH, month - 1);
        begin_time.set(Calendar.DATE, date);
        begin_time.set(Calendar.HOUR_OF_DAY, 0);
        begin_time.set(Calendar.MINUTE, 0);
        begin_time.set(Calendar.SECOND, 0);
        begin_time.set(Calendar.MILLISECOND, 0);
        return begin_time;
    }

    public static void resetTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 判断相同日期
     *
     * @param date1 日期1
     * @param date2 日期2
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if ((date1 == null) || (date2 == null)) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * 判断相同日历
     *
     * @param cal1 时间1
     * @param cal2 时间2
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if ((cal1 == null) || (cal2 == null)) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) && (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) && (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 判断是否同一时间(毫秒数相等)
     *
     * @param date1 日期1
     * @param date2 日期2
     */
    public static boolean isSameInstant(Date date1, Date date2) {
        if ((date1 == null) || (date2 == null)) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date1.getTime() == date2.getTime();
    }

    /**
     * 判断是否同一时间(毫秒数相等)
     *
     * @param cal1 日历1
     * @param cal2 日历2
     */
    public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
        if ((cal1 == null) || (cal2 == null)) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return cal1.getTime().getTime() == cal2.getTime().getTime();
    }

    /**
     * 判断本地时间是否相同
     *
     * @param cal1 日历1
     * @param cal2 日历2
     */
    public static boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
        if ((cal1 == null) || (cal2 == null)) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(14) == cal2.get(14)) && (cal1.get(13) == cal2.get(13)) && (cal1.get(12) == cal2.get(12))
                && (cal1.get(10) == cal2.get(10)) && (cal1.get(6) == cal2.get(6)) && (cal1.get(1) == cal2.get(1))
                && (cal1.get(0) == cal2.get(0)) && (cal1.getClass() == cal2.getClass());
    }

    /**
     * 判断时间start是否在end之前
     *
     * @param start 时间1
     * @param end   时间2
     */
    public static boolean isDateBefore(Date start, Date end) {
        GregorianCalendar sgc = new GregorianCalendar();
        GregorianCalendar egc = new GregorianCalendar();
        sgc.setTime(start);
        egc.setTime(end);
        return sgc.before(egc);
    }

    /**
     * 判断时间是否在当前区间内
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param date  时间
     */
    public static boolean isDateBetweenStartAndEnd(Date start, Date end, Date date) {
        return (isDateBefore(start, date) && isDateBefore(date, end));
    }

    /**
     * 判断时间是否在当前区间内或者等于开始时间或者结束时间
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param date  时间
     */
    public static boolean isDateBetweenStartAndEndOrEquals(Date start, Date end, Date date) {
        return ((isDateBefore(start, date) && isDateBefore(date, end)) || Objects.equals(start.getTime(), date.getTime()) || Objects.equals(end.getTime(), date.getTime()));
    }

    /**
     * 判断时间是否在当前区间内
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param date  时间
     */
    public static boolean isDateBetween(Date start, Date end, Date date) {
        int sd = TypeUtil.strToInt(dateToStr(start));
        int ed = TypeUtil.strToInt(dateToStr(end));
        int d = TypeUtil.strToInt(dateToStr(date));
        return (sd <= ed) && (sd <= d) && (d <= ed);
    }

    /**
     * 判断yyyyMMdd 小于
     *
     * @param date1 时间1
     * @param date2 时间2
     */
    public static boolean isDateLe(Date date1, Date date2) {
        int d1 = TypeUtil.strToInt(dateToStr(date1));
        int d2 = TypeUtil.strToInt(dateToStr(date2));
        return d1 <= d2;
    }

    /**
     * 判断yyyyMMdd 大于
     *
     * @param date1 时间1
     * @param date2 时间2
     */
    public static boolean isDateGe(Date date1, Date date2) {
        int d1 = TypeUtil.strToInt(dateToStr(date1));
        int d2 = TypeUtil.strToInt(dateToStr(date2));
        return d1 >= d2;
    }

    /**
     * 判断yyyyMM 区间
     */
    public static boolean yearMonthBetweenStartAndEnd(Date start, Date end, Date date) {
        int sd = TypeUtil.strToInt(YearMonthToStr(start).replace("-", ""));
        int ed = TypeUtil.strToInt(YearMonthToStr(end).replace("-", ""));
        int d = TypeUtil.strToInt(YearMonthToStr(date).replace("-", ""));
        return (sd <= ed) && (sd <= d) && (d <= ed);
    }

    /**
     * 判断yyyyMM 区间
     */
    public static boolean yearMonthBetweenStartAndEnd(Date start, Date end, String yearMonth) {
        int sd = TypeUtil.strToInt(YearMonthToStr(start).replace("-", ""));
        int ed = TypeUtil.strToInt(YearMonthToStr(end).replace("-", ""));
        int ym = TypeUtil.strToInt(yearMonth.replace("-", ""));
        return (sd <= ed) && (sd <= ym) && (ym <= ed);
    }

    /**
     * 判断时间是否在当前时间之后
     */
    public static boolean afterCurDate(Date date) {
        GregorianCalendar now = new GregorianCalendar();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        return c.after(now);
    }

    /**
     * 获取月份缩写
     */
    public static String getMonthStr(String str) {
        String[] monthOfYear = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        if ("01".equals(str)) {
            str = monthOfYear[0];
        } else if ("02".equals(str)) {
            str = monthOfYear[1];
        } else if ("03".equals(str)) {
            str = monthOfYear[2];
        } else if ("04".equals(str)) {
            str = monthOfYear[3];
        } else if ("05".equals(str)) {
            str = monthOfYear[4];
        } else if ("06".equals(str)) {
            str = monthOfYear[5];
        } else if ("07".equals(str)) {
            str = monthOfYear[6];
        } else if ("08".equals(str)) {
            str = monthOfYear[7];
        } else if ("09".equals(str)) {
            str = monthOfYear[8];
        } else if ("10".equals(str)) {
            str = monthOfYear[9];
        } else if ("11".equals(str)) {
            str = monthOfYear[10];
        } else if ("12".equals(str)) {
            str = monthOfYear[11];
        } else {
            str = "ERROR";
        }
        return str;
    }

    /**
     * 获取月份
     */
    public static int getNumByStrMonth(String strMonth) {
        if (strMonth.equals("January")) {
            return 1;
        }
        if (strMonth.equals("February")) {
            return 2;
        }
        if (strMonth.equals("March")) {
            return 3;
        }
        if (strMonth.equals("April")) {
            return 4;
        }
        if (strMonth.equals("May")) {
            return 5;
        }
        if (strMonth.equals("June")) {
            return 6;
        }
        if (strMonth.equals("July")) {
            return 7;
        }
        if (strMonth.equals("August")) {
            return 8;
        }
        if (strMonth.equals("September")) {
            return 9;
        }
        if (strMonth.equals("October")) {
            return 10;
        }
        if (strMonth.equals("November")) {
            return 11;
        }
        if (strMonth.equals("December")) {
            return 12;
        }
        return 0;
    }

    /**
     * 数字转字符
     */
    public static String IntegerTo(Integer i) {
        String str;
        if (i < 10) {
            str = "0" + i;
        } else {
            str = String.valueOf(i);
        }
        return str;
    }

    /**
     * 获取时间
     */
    public static String getShortTime(String time) {
        calendar = Calendar.getInstance();
        calendar.setTime(SDateTimeToDate(time));
        String shortstring;
        long now = Calendar.getInstance().getTimeInMillis();
        Date date = SDateTimeToDate(time);
        if (date == null) {
            return null;
        }
        long deltime = (now - date.getTime()) / 1000L;
        if (deltime > 31536000L) {
            shortstring = time;
        } else if (deltime > 86400L) {
            if ((int) (deltime / 86400L) <= 1) {
                shortstring = "昨天 " + IntegerTo(Integer.valueOf(calendar.get(11))) + ":"
                        + IntegerTo(Integer.valueOf(calendar.get(12)));
            } else if ((int) (deltime / 86400L) <= 2) {
                shortstring = "前天 " + IntegerTo(Integer.valueOf(calendar.get(11))) + ":"
                        + IntegerTo(Integer.valueOf(calendar.get(12)));
            } else if ((int) (deltime / 86400L) <= 186) {
                shortstring = IntegerTo(Integer.valueOf(calendar.get(2) + 1)) + "月"
                        + IntegerTo(Integer.valueOf(calendar.get(5))) + "日 "
                        + IntegerTo(Integer.valueOf(calendar.get(11))) + ":"
                        + IntegerTo(Integer.valueOf(calendar.get(12)));
            } else {
                shortstring = time;
            }
        } else if (deltime > 3600L) {
            if ((int) (deltime / 3600L) < 12) {
                shortstring = (int) (deltime / 3600L) + "小时前";
            } else if ((int) (deltime / 3600L) < 24) {
                shortstring = "昨天 " + IntegerTo(Integer.valueOf(calendar.get(11))) + ":"
                        + IntegerTo(Integer.valueOf(calendar.get(12)));
            } else {
                shortstring = "今天 " + IntegerTo(Integer.valueOf(calendar.get(11))) + ":"
                        + IntegerTo(Integer.valueOf(calendar.get(12)));
            }
        } else if (deltime > 60L) {
            shortstring = IntegerTo(Integer.valueOf((int) (deltime / 60L))) + "分前";
        } else if (deltime > 1L) {
            shortstring = "刚刚";
        } else {
            shortstring = "刚刚";
        }
        return shortstring;
    }

    /**
     * 获取时间
     */
    public static String getTime(Date date) {
        String todySDF = "今天 HH:mm";
        String yesterDaySDF = "昨天 HH:mm";
        String otherSDF = "M月d日 HH:mm";
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(now);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        if (dateCalendar.after(targetCalendar)) {
            sfd = new SimpleDateFormat(todySDF);
            time = sfd.format(date);
            return time;
        }
        targetCalendar.add(Calendar.DATE, -1);
        if (dateCalendar.after(targetCalendar)) {
            sfd = new SimpleDateFormat(yesterDaySDF);
            time = sfd.format(date);
            return time;
        }
        sfd = new SimpleDateFormat(otherSDF);
        time = sfd.format(date);
        return time;
    }

    /**
     * 获取时间戳
     */
    public static Integer DateToTimestamp(Date time) {
        Timestamp ts = new Timestamp(time.getTime());
        return Integer.valueOf((int) (ts.getTime() / 1000L));
    }

    /**
     * 字符串时间转化日期
     *
     * @param seconds
     * @return
     */
    public static Date timeStampToDate(String seconds) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(new Long(seconds));
        return SDateTimeToDate(d);
    }

    /**
     * 今天还剩余多少秒
     *
     * @return 今天剩余秒数
     */
    public static long getTodaySecondRemaining() {
        Calendar curDate = Calendar.getInstance();
        Calendar tomorrowDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH),
                curDate.get(Calendar.DATE) + 1, 0, 0, 0);
        return (tomorrowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
    }

    /**
     * @return 获得当天0点时间
     */
    public static Date getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();

    }

    /**
     * @return 获得当天24点时间
     */
    public static Date getTimesNight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    // 实现日期加一天的方法
    public static Date addDay(Date date, int n) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DATE, n);// 增加一天
        return cd.getTime();
    }

    // 获取某天零点
    public static Date getStartTimeOfDay(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.MILLISECOND, 0);
        return day.getTime();
    }

    /**
     * 获取一天的结束时间
     *
     * @param date 时间
     * @return end time of day
     */
    public static Date getEndTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 根据时间类型比较时间大小 String nowTime = new SimpleDateFormat("HH:MM").format(new
     * Date()); int i = DateCompare(nowTime,"06:00","HH:MM");
     *
     * @param source 来源
     * @param target 目标
     * @param type   "YYYY-MM-DD" "yyyyMMdd HH:mm:ss" 类型可自定义 传递时间的对比格式
     * @return 0 ：source和traget时间相同 1 ：source比traget时间大 -1：source比traget时间小
     * @throws Exception
     */
    public static int DateCompare(String source, String target, String type) throws Exception {
        int ret = 2;
        SimpleDateFormat format = new SimpleDateFormat(type);
        Date sourcedate = format.parse(source);
//		System.out.println(sourcedate);
        Date targetdate = format.parse(target);
//		System.out.println(targetdate);
        ret = sourcedate.compareTo(targetdate);
        return ret;
    }

    /**
     * 获取时间是否在区间内
     */
    public static boolean betweenTime(Date date, String beginTime, String endTime) {
        return betweenTime(date, beginTime, endTime, null);
    }

    /**
     * 获取时间是否在区间内
     */
    public static boolean betweenTime(Date date, String beginTime, String endTime, String format) {
        if (StringUtils.isBlank(format)) {
            format = "HH:mm:ss";
        }
        String nowTime = new SimpleDateFormat(format).format(date);
        //System.out.println(nowTime);
        try {
            int i1 = DateCompare(nowTime, beginTime, format);
            //System.out.println(i1);
            int i2 = DateCompare(nowTime, endTime, format);
            //System.out.println(i2);
            if (i1 >= 0 && i2 <= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("{}", e);
            return false;
        }
    }

    /**
     * 获取指定时间月天数
     *
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
