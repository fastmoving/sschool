package com.usoft.sschool_teacher.exception;

/**
 * @Author: 陈秋
 * @Date: 2019/5/8 13:59
 * @Version 1.0
 */
public class MyException extends RuntimeException{
    public MyException(){

    }
    public MyException(String message){
        super(message);
    }
}
