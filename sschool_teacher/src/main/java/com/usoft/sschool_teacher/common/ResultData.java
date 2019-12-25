package com.usoft.sschool_teacher.common;

import java.io.Serializable;

/**
 * @Author: 陈秋
 * @Date: 2019/5/5 14:09
 * @Version 1.0
 * 返回数据
 */
public class ResultData implements Serializable {

    private static final long serialVersionUID = 1L;

    private int status;

    private String message;

    private Object data;

    private Integer totalNumber;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }
}
