package com.usoft.sschool_manage.exception;

/**
 * @Author: 陈秋
 * @Date: 2019/9/5 17:46
 * @Version 1.0
 */
public class MyException extends RuntimeException {
    public MyException(){

    }
    public MyException(String message){
        super(message);
    }
}
