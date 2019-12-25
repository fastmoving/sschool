package com.usoft.sschool_teacher.enums;

/**
 * @Author: 陈秋
 * @Date: 2019/5/17 16:36
 * @Version 1.0
 */
public enum AbsentEnums {
    /**
     * 请假审核状态
     */
    ABSENT_POST( 2,"审核通过"),

    ABSENT_NO(3,"审核驳回"),

    ABSENT_WAIT(1,"待审核");

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

    AbsentEnums(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static String getMessage(int status){
        AbsentEnums[] enums = AbsentEnums.values();
        for (int i=0;i<enums.length;i++){
            if (enums[i].getStatus() == status){
                return enums[i].getMessage();
            }
        }
        return "";
    }
}
