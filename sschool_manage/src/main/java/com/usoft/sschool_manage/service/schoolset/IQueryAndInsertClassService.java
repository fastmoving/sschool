package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.pojo.XnIntegralRule;

/**
 * @Author: 陈秋
 * @Date: 2019/7/25 17:22
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
