package com.usoft.sschool_teacher.exception;

/**
 * @Author: 陈秋
 * @Date: 2019/5/8 14:10
 * @Version 1.0
 */
public class CustomException {

    public static void customeIf(int i) throws MyException{
        if (i == 0){
            throw new MyException();
        }
    }
}
