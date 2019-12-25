package com.usoft.sschool_manage.enums;

/**
 * @Author: 陈秋
 * @Date: 2019/9/23 11:34
 * @Version 1.0
 */
public enum MealEnums {
    TEACHER(2,"教师"),

    STUDENT(1,"学生");
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

    MealEnums(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static String getMessage(int status){
        MealEnums[] enums = MealEnums.values();
        for (int i=0;i<enums.length;i++){
            if (status == enums[i].getStatus()){
                return enums[i].getMessage();
            }
        }
        return "";
    }
}
