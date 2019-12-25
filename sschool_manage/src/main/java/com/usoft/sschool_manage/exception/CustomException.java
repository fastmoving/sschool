package com.usoft.sschool_manage.exception;

/**
 * @Author: 陈秋
 * @Date: 2019/9/5 17:47
 * @Version 1.0
 */
public class CustomException {

    public static void customeIf(int i) throws MyException{
        if (i == 0){
            throw new MyException();
        }
    }
}
