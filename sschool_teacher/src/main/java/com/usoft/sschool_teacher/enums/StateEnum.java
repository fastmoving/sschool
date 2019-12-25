package com.usoft.sschool_teacher.enums;

/**
 * @Author: 陈秋
 * @Date: 2019/5/10 17:07
 * @Version 1.0
 */
public enum StateEnum {
    NO(1,"否"),
    YES(2,"是"),
    TEACHERLOOK(3,"已审批")
    ;
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

    StateEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static String getState(int status){
        StateEnum[] enums = StateEnum.values();
        for (int i=0;i<enums.length;i++) {
            if (status==enums[i].getStatus()){
                return enums[i].getMessage();
            }
        }
        return "";
    }
}
