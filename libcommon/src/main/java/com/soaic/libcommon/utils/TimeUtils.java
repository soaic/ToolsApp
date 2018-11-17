package com.soaic.libcommon.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.util.Locale.getDefault;

/**
 * Created by DDY-03 on 2017/10/17.
 */

public class TimeUtils {


    public static final long SECOND = 1000L;
    public static final long MINUTE = 60 * SECOND;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final long MONTH = 30 * DAY;
    public static final long YEAR = 365 * MONTH;


    /**
     * 将时间字符串转为时间戳
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(String time, String pattern) {
        try {
            return new SimpleDateFormat(pattern, getDefault()).parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param millis  毫秒时间戳
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String millis2String(long millis, String pattern) {
        return new SimpleDateFormat(pattern, getDefault()).format(new Date(millis));
    }

    /**
     * 将日期转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param data    日期
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date data, String pattern) {
        return new SimpleDateFormat(pattern, getDefault()).format(data);
    }


    /**
     * 时间字符串转日期
     *
     * @param data    日期
     * @param pattern 格式
     * @return Date
     */
    public static Date string2Date(String data, String pattern) {
        try {
            return new SimpleDateFormat(pattern, getDefault()).parse(data);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 时间转时间
     *
     * @param time
     * @return
     */
    public static String string2String(String time, String pattern1, String pattern2) {
        long times = string2Millis(time, pattern1);
        if (times == -1) {
            return time;
        } else {
            return millis2String(times, pattern2);
        }
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param millis 毫秒时间戳
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果在1秒钟内，显示刚刚</li>
     * <li>如果是今天的，显示15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况 2017-01-01</li>
     * </ul>
     */
    @SuppressLint("DefaultLocale")
    public static String getFriendlyTimeSpanByNow(long millis) {
        //当前日历
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTimeInMillis(System.currentTimeMillis());
        calendarNow.setTimeZone(TimeZone.getDefault());
        //比较日历
        Calendar calendarMils = Calendar.getInstance();
        calendarMils.setTimeInMillis(millis);
        calendarMils.setTimeZone(TimeZone.getDefault());
        if (calendarNow.get(Calendar.YEAR) == calendarMils.get(Calendar.YEAR)) {
            int diff = calendarMils.get(Calendar.DAY_OF_YEAR) - calendarNow.get(Calendar.DAY_OF_YEAR);
            if (diff == 0) {
                return String.format(getDefault(), "%tR", new Date(millis));//12:00
            } else if (diff == -1) {
                return "昨天";//%tR返回12:00
            } else if (diff < -1 && diff >= -6) {
                return String.format(getDefault(), "%tA", new Date(millis)).replace("星期", "周");//%tA星期几
            } else {
                return String.format(getDefault(), "%tF", new Date(millis)).replace("-", "/");
            }
        } else {
            return String.format(getDefault(), "%tF", new Date(millis)).replace("-", "/");
        }
    }

    /**
     * 是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(date1);
        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date2);
        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                .get(Calendar.DAY_OF_MONTH);
    }

    public static long getDateBetweenYear(Date date1, Date date2) {
        long days = getDateBetweenDay(date1, date2);
        return days / 365;
    }

    public static String getDateBetweenYearDecimal(Date date1, Date date2) {
        long seconds = getDateBetweenSecond(date1, date2);
        int yt = 365 * 24 * 60 * 60;
        double year = NumberUtils.doublePlus(seconds / yt, (double) seconds % yt / yt);
        return NumberUtils.keepDecimalDigit(year, 8);
    }

    public static long getDateBetweenMonth(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int result = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
        int month = (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 12;
        if (result <= 0 && c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH) < 0) {
            return Math.abs(month + result) - 1;
        }
        return Math.abs(month + result);
    }

    public static long getDateBetweenWeek(Date date1, Date date2) {
        long seconds = getDateBetweenSecond(date1, date2);
        return seconds / 60 / 60 / 24 / 7;
    }

    public static long getDateBetweenDay(Date date1, Date date2) {
        long seconds = getDateBetweenSecond(date1, date2);
        return seconds / 60 / 60 / 24;
    }

    public static long getDateBetweenHour(Date date1, Date date2) {
        long seconds = getDateBetweenSecond(date1, date2);
        return seconds / 60 / 60;
    }

    public static long getDateBetweenMinute(Date date1, Date date2) {
        long seconds = getDateBetweenSecond(date1, date2);
        return seconds / 60;
    }

    public static long getDateBetweenSecond(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        return Math.abs(time2 - time1) / SECOND;
    }

    /**
     * 获取一天中的几点
     *
     * @param date
     * @return
     */
    public static int getDateHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }
}
