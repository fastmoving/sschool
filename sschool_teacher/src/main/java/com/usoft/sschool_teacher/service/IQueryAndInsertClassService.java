package com.usoft.sschool_teacher.service;

import com.usoft.smartschool.pojo.XnIntegralRule;

/**
 * @Author: 陈秋
 * @Date: 2019/6/19 15:09
        * @Version 1.0
        */
public interface IQueryAndInsertClassService {

    /**
     * 检查是否存在行为
     */
    XnIntegralRule queryIntegralRule(Integer function);

    /**
     * 执行行为
     */
    int insertIntegral(Integer function,Integer num);
}
