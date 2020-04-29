package com.usoft.sschool_teacher.exception;

/**
 * @Author: 陈秋
 * @Date: 2019/5/8 13:59
 * @Version 1.0
 */
public class MyException extends RuntimeException{

    private Integer status;

    private Boolean success;

    public MyException(){

    }
    public MyException(String message){
        super(message);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MyException(Integer status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public MyException(Integer status,String message, Boolean success) {
        super(message);
        this.status = status;
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
