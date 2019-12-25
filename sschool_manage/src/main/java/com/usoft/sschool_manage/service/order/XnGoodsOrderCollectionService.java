package com.usoft.sschool_manage.service.order;

import com.usoft.smartschool.util.MyResult;

/**
 * 商品订单统计
 * @Author jijh
 * @Date 2019/5/21 14:16
 */
public interface XnGoodsOrderCollectionService {


    /**
     * 成功订单总数和每单平均金额
     * @param beginTime
     * @param endTime
     * @return
     */
    MyResult TotalOrderAndAveragePrice(String beginTime, String endTime);

    /**
     * 总的订单统计 按照订单的状态
     * @param type 类型 1.现金 2.积分
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    MyResult OrderTypeCollection(Integer type,String beginTime, String endTime);


    /**
     * 年统计
     * @param beginYear 开始年
     * @param endYear 结束年
     * @return
     */
    MyResult YearDoneCollection(String beginYear, String endYear);

    /**
     * 月统计
     * @param year 哪一年
     *
     * @return
     */
    MyResult MonthDoneCollection(String year);

    /**
     * 日统计
     * @param month 哪一月
     *
     * @return
     */
    MyResult DaysDoneCollection(String month);

    /**
     * 时
     * @param day 某一天
     * @return
     */
    MyResult hourDoneCollection(String day);

}
