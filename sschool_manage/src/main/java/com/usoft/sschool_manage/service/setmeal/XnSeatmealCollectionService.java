package com.usoft.sschool_manage.service.setmeal;

import com.usoft.smartschool.util.MyResult;

/**
 * 套餐统计
 * @Author jijh
 * @Date 2019/5/15 13:56
 */
public interface XnSeatmealCollectionService {

    /**
     * 套餐统计 绑定人数， 订单数， 缴费总金额
     * @return
     */
    MyResult totalCollection();


    /**
     * 绑定角色统计图
     * @return
     */
    MyResult roleBindingCollection();


    /**
     * 订单统计图
     * @return
     */
    MyResult orderNumberCollection();


    /**
     * 订单金额统计
     * @return
     */
    MyResult orderPriceCollection();

}
