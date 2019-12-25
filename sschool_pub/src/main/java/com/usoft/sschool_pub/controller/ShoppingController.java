package com.usoft.sschool_pub.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_pub.serivice.ShoppingService;
import com.usoft.sschool_pub.util.SystemParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author wlw
 * @data 2019/4/25 0025-16:53
 */
@RestController
@RequestMapping("/pub/shopping")
@CrossOrigin
public class ShoppingController {
    private static final Logger logger= LoggerFactory.getLogger(ShoppingController.class);
    @Resource
    private ShoppingService shoppingService;

    /**
     * 商品一级分类
     * @param goodsType
     * @return
     */
    @GetMapping("level")
    public MyResult level(Byte goodsType){
        return shoppingService.level(goodsType);
    }

    /**
     * 二级分类
     * @param level
     * @return
     */
    @GetMapping("childLevel")
    public MyResult childLevel(Integer level){
        return shoppingService.childLevel(level);
    }
    /**
     * 展示商品的信息
     * @param mallType
     * @param level
     * @return
     */
    @GetMapping("/searchGoods")
    public MyResult searchGoods(Integer mallType,String level,String childLevel,Integer pageNo,Integer pageSize){
        Integer type = SystemParam.getType();
        return shoppingService.all(mallType,level,childLevel,pageNo,pageSize);
    }

    /**
     * 选中商品的详细信息
     * @param goodsId
     * @return
     */
    @GetMapping("/oneGoodsInfo")
    public MyResult oneGoodsInfo(Integer goodsId){
        return shoppingService.oneGoodsInfo(goodsId);
    }

    /**
     * 保存订单信息
     * @param goodsId
     * @param goodsName
     * @param number
     * @param price
     * @param totalPrice
     * @param type
     * @return
     */
    @PostMapping("/saveGoodsOrder")
    public MyResult saveGoodsOrder(Integer goodsId,Integer addressId, String goodsName, Integer number, BigDecimal price, BigDecimal totalPrice, Byte type){
        Integer type1 = SystemParam.getType();
        return shoppingService.saveGoodsOrder(goodsId,addressId,goodsName,number,price,totalPrice,type,type1);
    }

    /**
     * 根据订单id查询订单信息
     * @param orderId
     * @return
     */
    @GetMapping("/searchByTrandNo")
    public MyResult searchByTrandNo(Integer orderId){
        return shoppingService.searchByTrandNo(orderId);
    }

    /**
     * 添加收货地址
     * @param orderId
     * @param addressId
     * @return
     */
    @PostMapping("/addAddressId")
    public MyResult addAddressId(Integer orderId,Integer addressId){
        return shoppingService.addAddressId(orderId,addressId);
    }
    /**
     * 查询我的订单
     * @param orderState
     * @return
     */
    @GetMapping("/searchMyOrder")
    public MyResult searchMyOrder(Byte orderState,Integer pageNo,Integer pageSize){
        Integer type = SystemParam.getType();
        return shoppingService.searchMyOrder(orderState,type,pageNo,pageSize);
    }
    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @PostMapping("/deleteOrder")
    public MyResult deleteOrder(String orderId){
        return shoppingService.deleteOrder(orderId);
    }
}

