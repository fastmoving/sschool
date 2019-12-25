package com.usoft.sschool_manage.enums;

/**
 * author : 陈秋
 * time : 2019-09-10
 */
public enum TimeTypeEnums {
    /**
     * 时间类型 1、年 2、月 3、学期
     */
    SEMESTER(3,"一学期"),

    MONTH(2,"一个月"),

    YEAS(1,"一年");
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

    TimeTypeEnums(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static String getMessage(int status){
        TimeTypeEnums[] enums = TimeTypeEnums.values();
        for (int i=0;i<enums.length;i++){
            if(status == enums[i].getStatus()){
                return enums[i].getMessage();
            }
        }
        return "";
    }
}
