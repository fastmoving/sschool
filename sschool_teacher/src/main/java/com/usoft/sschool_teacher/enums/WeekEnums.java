package com.usoft.sschool_teacher.enums;

/**
 * @Author: 陈秋
 * @Date: 2019/5/24 18:58
 * @Version 1.0
 */
public enum WeekEnums {

    SUNDAY(0,"星期天"),

    MONDAY(1,"星期一"),

    TUESDAY(2,"星期二"),

    WEDNESDAY(3,"星期三"),

    THURSDAY(4,"星期四"),

    FRIDAY(5,"星期五"),

    SATURDAY(6,"星期六");
    private int status;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    WeekEnums(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static String getMessage(int status){
        WeekEnums[] enums = WeekEnums.values();
        for (int i=0;i<enums.length;i++){
            if (enums[i].getStatus()==status){
                return enums[i].getMessage();
            }
        }
        return "";
    }
}
