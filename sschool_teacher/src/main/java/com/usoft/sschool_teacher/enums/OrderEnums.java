package com.usoft.sschool_teacher.enums;

/**
 * @Author: 陈秋
 * @Date: 2019/5/22 10:52
 * @Version 1.0
 * 订单状态 1.待支付 2.已支付  3.已取消  4.已删除 , 9.已成功
 */
public enum OrderEnums {
    ORDER_WAIT(1,"待支付"),

    ORDER_PAID(2,"已支付"),

    ORDER_CANC(3,"已取消"),

    ORDER_DELETE(4,"已删除"),

    ORDER_SUCCESS(9,"已成功");

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

    OrderEnums(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static String getMessage(int status){
        OrderEnums[] eunms = OrderEnums.values();
        for (int i=0;i<eunms.length;i++){
            if (status==eunms[i].getStatus()){
                return eunms[i].getMessage();
            }
        }
        return "";
    }
}
