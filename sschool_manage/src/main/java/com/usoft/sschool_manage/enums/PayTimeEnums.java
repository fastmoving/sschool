package com.usoft.sschool_manage.enums;

/**
 * @Author: 陈秋
 * @Date: 2019/9/23 15:08
 * @Version 1.0
 */
public enum PayTimeEnums {
    /**
     * 时间类型 1、年 2、月 3、学期
     */
    YEAR(1,365),

    MONTH(2,30),

    SIX_MONTH(3,180);
    public int status;
    public int timeType;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    PayTimeEnums(int status, int timeType) {
        this.status = status;
        this.timeType = timeType;
    }

    public static Integer getTimeType(int status){
        PayTimeEnums[] enums = PayTimeEnums.values();
        for (int i=0;i<enums.length;i++){
            if (enums[i].getStatus() == status){
                return enums[i].getTimeType();
            }
        }
        return 0;
    }
}
