package com.usoft.sschool_manage.util.personal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单号生成规则
 * @Author jijh
 * @Date 2019/5/16 14:00
 */
public class OrderNumberUtil {



    private static final char[] number = {'0','1','2','3','4','5','6','7','8','9'};

    public static  String getSetMealOrderNumber(Integer setId, Integer stuId){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0; i<6 ; i++){
            stringBuffer.append(number[(int)(Math.random()*10)]);
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMss");
        String today = sdf.format(date);
        stringBuffer.append(today);
        Integer endNumber = setId * stuId;
        String end = String.valueOf(endNumber);
        while(end.length()<8){
            end = "0"+end;
        }

        stringBuffer.append(end);
        return stringBuffer.toString();
    }


    public static void main(String[] args){
        System.out.println(getSetMealOrderNumber(21,23));
        String phone = "13565245896";
        System.out.println(phone.substring(5));
    }

}
