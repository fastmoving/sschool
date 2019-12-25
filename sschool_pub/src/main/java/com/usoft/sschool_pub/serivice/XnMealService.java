package com.usoft.sschool_pub.serivice;

import com.usoft.smartschool.util.MyResult;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wlw
 * @file
 * @create 2019-09-05 17:37
 */
public interface XnMealService {
    /**
     * 查询套餐列表
     * @return
     */
    MyResult mealList(Integer timeType);

    /**
     * 保存套餐订单
     * @param mailId
     * @return
     */
    MyResult saveMealOrder(Integer mailId, Integer timeType, Integer number, BigDecimal price);

    /**
     * 查询该学生所有绑定的家长
     * @return
     */
    MyResult phoneList();

    /**
     * 查询我的套餐
     * @return
     */
    MyResult myMail();
    /**
     * 绑定家长
     * @param phone
     * @return
     */
    MyResult bindPhone(String phone);
}
