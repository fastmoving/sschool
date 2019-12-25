package com.usoft.sschool_manage.util.test;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author jijh
 * @Date 2019/6/14 16:05
 */
public class DaoProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        System.out.println("Before Method Invoke");
        proxy.invokeSuper(object, objects);
        System.out.println("After Method Invoke");

        return object;
    }


    public static void main(String[] args){
        DaoProxy daoProxy = new DaoProxy();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TestSpring.class);
        enhancer.setCallback(daoProxy);

        TestSpring dao = (TestSpring)enhancer.create();
        dao.update();
        dao.select();
    }

}
