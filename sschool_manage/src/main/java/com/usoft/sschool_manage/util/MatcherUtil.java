package com.usoft.sschool_manage.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author jijh
 * @Date 2019/6/4 14:31
 */
public class MatcherUtil {


    private static Matcher matcher;

    private static Pattern pattern;


    /**
     * 匹配YYYY-MM-DD
     *
     * @return
     */
    public static boolean isYYYY_MM_DD(String birthday) {

        String regex = "^[\\d]{4}-[\\d]{2}-[\\d]{2}$";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(birthday);
        boolean match = matcher.matches();
        if (match) {
            String[] strs = birthday.split("-");
            int year = Integer.valueOf(strs[0]);
            int month = Integer.valueOf(strs[1]);
            int day = Integer.valueOf(strs[2]);
            if (year < 1900 || year > 2099) {
                return false;
            }
            if (month < 1 || month > 12) {
                return false;
            }
            boolean idLeapYear = (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0));

            if (day < 1) {
                return false;
            }
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if (day > 31) {
                        return false;
                    }
                    ;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if (day > 30) {
                        return false;
                    }
                    ;
                    break;
                case 2:
                    if (idLeapYear) {
                        if (day > 29) return false;
                    } else {
                        if (day > 28) return false;
                    }
                    ;
                    break;
            }
        } else {
            return match;
        }
        return true;
    }

    public static void main(String[] args){
        String a = "2019-02-29";
        System.out.println(isYYYY_MM_DD(a));
    }
}
