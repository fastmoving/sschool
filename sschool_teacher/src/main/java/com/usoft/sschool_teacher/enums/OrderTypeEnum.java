package com.usoft.sschool_teacher.enums;

import org.aspectj.weaver.ast.Or;

/**
 * @Author: 陈秋
 * @Date: 2019/5/29 17:27
 * @Version 1.0
 */
public enum OrderTypeEnum {
    ON_LINE(1,"线上"),

    OFF_LINE(2,"线下");
    /**
     * 购买类型 1.线上 2.线下
     */

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

    OrderTypeEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static String getMessage(int status){
        OrderTypeEnum[] enums = OrderTypeEnum.values();
        for (int i=0;i<enums.length;i++){
            if (enums[i].getStatus()==status){
                return enums[i].getMessage();
            }
        }
        return "";
    }
}
