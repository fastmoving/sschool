package com.usoft.sschool_manage.service.live;

import com.usoft.smartschool.util.MyResult;

/**
 * 精品课堂审核规则
 * @Author jijh
 * @Date 2019/5/22 16:44
 */
public interface XnTopQualityClassRuleService {

    /**
     * 查看精品课堂规则
     * @return
     */
    MyResult selectXnTopQualiyClassRule();


    /**
     * 添加或者修改精品课堂规则
     * @param isPay 是否付费
     * @param price 价格
     * @return
     */
    MyResult addOrUpdateXnTopQualiyClassRule(Integer isPay, String price);





}
