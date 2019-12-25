package com.usoft.sschool_pub.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @Author jijh
 * @Date 2019/5/5 11:10
 */
public class TimeUtil {
    /**
     * 时间毫秒数转换为yyyy-MM-dd HH:mm:ss
     * @param time
     * @return
     */
    public static String LongToYYYYMMDDHHMMSS(String time){
        Date date=new Date(Long.parseLong(time));
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time=formatter.format(date);
        return time;
    }

    /**
     * 时间返回年月
     * @param time
     * @return
     */
    public static String timeGetMonth(Date time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times = simpleDateFormat.format(time);
        String str= times.substring(0,3);
        String str1=times.substring(4,5);
        return str+"-"+str1;
    }
    /**
     * 时间转化为字符串1 yyyy-MM-dd
     * @param date
     * @return
     */
    public static String TimeToYYYYMMDD(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(date);
        return time;
    }

    /**
     * 时间转化为字符串2 yyyy-MM
     * @param date
     * @return
     */
    public static String TimeToYYYYMM(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String time = simpleDateFormat.format(date);
        return time;
    }

    /**
     * 时间转化为字符串3 yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String TimeToYYYYMMDDHHMMSS(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        return time;
    }

    /**
     * string转data
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date YYYYMMDDHHMMSSToTime(String time) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        return date;
    }

    /**
     * yyyymmdd转data
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date YYYYMMDDToTime(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=simpleDateFormat.parse(time);
        return date;
    }
}
