package com.usoft.sschool_manage.service.order;

import com.usoft.smartschool.util.MyResult;

/**
 * 商品订单
 * @Author jijh
 * @Date 2019/5/20 9:29
 */
public interface XnGoodsOrderService {


    /**
     * 订单列表
     * @param type  类型 1.现金 2.积分
     * @param userName 用户名
     * @param orderState 订单状态 1.待支付 2.已支付  3.已取消 4.已删除
     * @param buyTimeBegin 下单开始时间
     * @param buyTimeEnd 下单结束时间
     * @param priceBegin 订单金额大于等于
     * @param priceEnd 订单金额小于等于
     * @param phone 联系方式
     * @param orderNumber 订单号码
     * @param  pageNo 页数
     * @param pageSize  每页大小
     * @return
     */
    MyResult listGoodsOrder(Integer type, String userName, Integer orderState, String buyTimeBegin, String buyTimeEnd, String priceBegin,
                            String priceEnd, String phone, String orderNumber,Integer pageNo, Integer pageSize);


    /**
     * 查看订单详情
     * @param id
     * @return
     */
    MyResult selectGoodsOrder(Integer id);
}
