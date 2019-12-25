package com.usoft.sschool_manage.util;

import com.usoft.smartschool.util.MyResult;

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

    public static Date YYYYMMDDHHMMSSToTime(String time) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = simpleDateFormat.parse(time);
        return date;

    }

}
