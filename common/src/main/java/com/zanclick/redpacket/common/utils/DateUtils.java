package com.zanclick.redpacket.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author tuchuan
 * @description
 * @date 2017/7/6 14:41
 */
public class DateUtils {
    public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String PATTERN_MM_dd_HH_mm = "MM-dd HH:mm";
    private static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    private static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    private static final String yyyy_MM = "yyyy-MM";


    /**
     * 将日期转为字符串 yyyy-mm-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String yyyy_MM_dd_HH_mm_ss(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(yyyy_MM_dd_HH_mm_ss);
        LocalDateTime localDateTime = dateToDateTime(date);
        return formatter.format(localDateTime);
    }
    public static String yyyy_MM_dd(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(yyyy_MM_dd);
        LocalDateTime localDateTime = dateToDateTime(date);
        return formatter.format(localDateTime);
    }

    /**
     * 将日期转为字符串 yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String yyyyMMddHHmmss(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(yyyyMMddHHmmss);
        LocalDateTime localDateTime = dateToDateTime(date);
        return formatter.format(localDateTime);
    }




    /**
     * date 转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToDateTime(Date date) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime;
    }

    /**
     * 加月数
     *
     * @param date 初始时间
     * @param num  数值
     * @return
     */
    public static Date addMonth(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);
        return calendar.getTime();
    }

    /**
     * 年
     *
     * @param date 初始时间
     * @param num  数值
     * @return
     */
    public static Date addYear(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, num);
        return calendar.getTime();
    }

    /**
     * 加日数
     *
     * @param date 初始时间
     * @param num  数值

     * @return
     */
    public static Date addDay(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, num);
        return calendar.getTime();
    }


    /**
     * 加小时
     *
     * @param date 初始时间
     * @param num  数值
     * @return
     */
    public static Date addHour(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, num);
        return calendar.getTime();
    }

    /**
     * 加分钟
     *
     * @param date 初始时间
     * @param num  数值
     * @return
     */
    public static Date addMinute(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, num);
        return calendar.getTime();
    }

    /**
     * @return java.lang.String
     * @description(将 date 转换成指定格式的字符串)
     */
    public static String formatDate(Date date, String format) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        return localDateTime.format(formatter);
    }

    /**
     * 加月数
     *
     * @param date 初始时间
     * @param num  数值
     * @return
     */
    public static Date addSecond(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, num);
        return calendar.getTime();
    }

    /**
     * 加月数
     *
     * @param date 初始时间
     * @param num  数值
     * @param type 类型，年月日
     * @return
     */
    public static Date addTime(Date date, Integer num, Integer type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, num);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween2(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
        } catch (Exception e) {
            e.printStackTrace();
            return 10000;
        }
        try {
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        int days = Integer.parseInt(String.valueOf(between_days));
        return days;
    }


    /**
     * 计算时间差
     *
     * @param date
     */
    public static Long nowTimeDifference(Date date) {
        Calendar calendar = Calendar.getInstance();
        Long currentTime = calendar.getTimeInMillis();
        calendar.setTime(date);
        Long targetTme = calendar.getTimeInMillis();
        return (currentTime - targetTme) / 1000;
    }

    /**
     * type=0时 获取某一天的0点0分0秒   type=1时   获取某一天23点59分59秒
     *
     * @param date
     * @param type
     * @return
     */
    public static String getStartOrEndTime(String date, int type) {
        String time = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        try {
            calendar1.setTime(format.parse(date));
            if (type == 0) {
                calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                        0, 0, 0);
                Date beginOfDate = calendar1.getTime();
                time = dateFormat.format(beginOfDate);
            } else if (type == 1) {
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(format.parse(date));
                calendar1.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
                        23, 59, 59);
                Date endOfDate = calendar1.getTime();
                time = dateFormat.format(endOfDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
