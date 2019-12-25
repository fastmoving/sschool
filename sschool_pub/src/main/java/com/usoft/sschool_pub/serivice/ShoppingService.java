package com.usoft.sschool_pub.serivice;

import com.usoft.smartschool.util.MyResult;

import java.math.BigDecimal;

/**
 * @author wlw
 * @data 2019/4/25 0025-16:54
 */
public interface ShoppingService {
    //查询商品的分类
    MyResult level(Byte goodsType);
    //商品二级分类
    MyResult childLevel(Integer level);
    //显示所有商品
    MyResult all(Integer mallType,String level,String childLevel,Integer pageNo,Integer pageSize);
    //选中商品详情
    MyResult oneGoodsInfo(Integer goodsId);
    //保存订单信息
    MyResult saveGoodsOrder(Integer goodsId,Integer addressId, String goodsName, Integer number, BigDecimal price, BigDecimal totalPrice, Byte type,Integer type1);
    //根据订单id查询订单信息
    MyResult searchByTrandNo(Integer orderId);
    //订单添加收货地址
    MyResult addAddressId(Integer orderId,Integer addressId);
    //查询我的订单
    MyResult searchMyOrder(Byte orderState,Integer type,Integer pageNo,Integer pageSize);
    //删除订单
    MyResult deleteOrder(String orderId);
}
