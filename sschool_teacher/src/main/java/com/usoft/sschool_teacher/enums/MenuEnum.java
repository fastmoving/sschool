package com.usoft.sschool_teacher.enums;

/**
 * author : 陈秋
 * time : 2019-05-25
 */
public enum MenuEnum {
    /**
     * 饭点 1.早  2.中  3.晚
     */
   MORNING(1,"早上"),

    NOONING(2,"中午"),

    NIGHT(3,"晚上");

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

    MenuEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static String getMessage(int status){
        MenuEnum[] enums = MenuEnum.values();
        for(int i=0;i<enums.length;i++){
            if (enums[i].getStatus() == status){
                return enums[i].getMessage();
            }
        }
        return "";
    }
}
