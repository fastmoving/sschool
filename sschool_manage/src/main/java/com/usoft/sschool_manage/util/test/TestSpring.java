package com.usoft.sschool_manage.util.test;


import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author jijh
 * @Date 2019/6/14 15:25
 */
public class TestSpring {

    public void update(){
        System.out.println("update");
    }

    public void select(){
        System.out.println("select");
    }

}


