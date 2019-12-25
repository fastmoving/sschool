package com.usoft.sschool_teacher.enums;

/**
 * @Author: 陈秋
 * @Date: 2019/6/18 16:21
 * @Version 1.0
 */
public enum BuyEnum {
    /**
     * 支付方式 1微信、2支付宝
     */
    WEIXIN(1,"微信"),

    ZHIFUBAO(2,"支付宝");
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

    BuyEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static String getMessage(int status){
        BuyEnum[] enums = BuyEnum.values();
        for (int i=0;i<enums.length;i++){
            if (status == enums[i].getStatus()){
                return enums[i].getMessage();
            }
        }
        return "";
    }
}
