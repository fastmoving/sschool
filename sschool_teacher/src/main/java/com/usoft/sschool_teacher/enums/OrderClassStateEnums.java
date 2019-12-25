package com.usoft.sschool_teacher.enums;

/**
 * @Author: 陈秋
 * @Date: 2019/6/5 20:22
 * @Version 1.0
 */
public enum OrderClassStateEnums {
    /**
     * 订单状态 1.待支付 2.已支付  3.已取消
     */
    ORDER_CLASS_STATE_WAIT(1,"待支付"),

    ORDER_CLASS_STATE_SUCCESS(2,"已支付"),

    ORDER_CLASS_STATE_ERROR(3,"已取消");
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

    OrderClassStateEnums(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static String getMessage(int status){
        OrderClassStateEnums[] enums = OrderClassStateEnums.values();
        for(int i=0;i<enums.length;i++){
            if (status==enums[i].getStatus()){
                return enums[i].getMessage();
            }
        }
        return "";
    }
}
