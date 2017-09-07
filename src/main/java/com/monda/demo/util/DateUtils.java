package com.monda.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yangjian
 * @since 2017-07-24
 */
public class DateUtils {

    /**
     * 根据指定的格式获取当前时间
     * @param format
     * @return
     */
    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat time = new SimpleDateFormat(format);
        return time.format(date);
    }

    public static String dateToString(Date date, String formatStr){
        String dateStr = "";
        if (null == date){
            return dateStr;
        }
        if (null == formatStr || "" == formatStr){
            formatStr = "yyyy-MM-dd";
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        dateStr = format.format(date);
        return dateStr;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 字符串转 Date 对象
     * @param dateStr
     * @return
     */
    public static Date stringToDate(String dateStr, String formatStr) {

        if (null == dateStr) {
            return new Date();
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    public static Date stringToDate(String dateStr) {
        return stringToDate(dateStr, "yyyy-MM-dd");
    }

    public static String getCurrentTime2() {
        Date date = new Date();
        SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd");
        return time.format(date);
    }
}
