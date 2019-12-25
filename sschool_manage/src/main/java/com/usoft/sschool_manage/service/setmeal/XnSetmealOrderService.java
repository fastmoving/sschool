package com.usoft.sschool_manage.service.setmeal;

import com.usoft.smartschool.util.MyResult;

/**
 * 套餐定单详情
 * @Author jijh
 * @Date 2019/5/13 15:41
 */
public interface XnSetmealOrderService {

    /**
     * 套餐订单列表
     * @param studentName 学生姓名
     * @param phone 家长号码
     * @param orderTimeBegin 下单开始时间
     * @param orderTimeEnd 下单结束时间
     * @param priceBegin 订单大于等于金额
     * @param priceEnd 订单小于等于金额
     * @param setMealId 套餐id
     * @param ordernumber 订单号码
     * @param orderState 订单状态
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    MyResult listSetmealOrder(String studentName, String phone, String orderTimeBegin, String orderTimeEnd,
                              String priceBegin, String priceEnd, Integer setMealId, String ordernumber,
                              Integer orderState,Integer pageNo,Integer pageSize);


    /**
     * 查询订单详情
     * @param orderId 订单ID
     * @return
     */
    MyResult selectSetmealOrder(Integer orderId);


    /**
     * 绑定关系
     * @param orderId 订单id
     * @return
     */
    MyResult listBindingDetail(Integer orderId);

}
