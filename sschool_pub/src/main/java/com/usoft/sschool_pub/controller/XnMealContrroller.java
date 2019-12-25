package com.usoft.sschool_pub.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_pub.serivice.XnMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author wlw
 * @file    套餐
 * @create 2019-09-05 17:35
 */
@Controller
@RequestMapping("/pub/meal")
@ResponseBody
@CrossOrigin
public class XnMealContrroller {
    @Autowired
    private XnMealService xnMealService;

    /**
     * 查询套餐列表
     * @return
     */
    @GetMapping("mealList")
    public MyResult mealList(Integer timeType){
        if (timeType==null || "".equals(timeType) || "null".equals(timeType)){
            timeType=1;
        }
        return xnMealService.mealList(timeType);
    }

    /**
     * 保存套餐订单
     * @param mailId 套餐id
     * @param timeType 1、年 2、月
     * @param number 数量
     * @param price 价格
     * @return
     */
    @PostMapping("saveMealOrder")
    public MyResult saveMealOrder(Integer mailId, Integer timeType, Integer number, BigDecimal price){
        return xnMealService.saveMealOrder(mailId, timeType, number, price);
    }
    /**
     * 查询学生绑定的其他家长
     * @return
     */
    @GetMapping("phoneList")
    public MyResult phoneList(){
        return xnMealService.phoneList();
    }

    /**
     * 查询我的套餐
     * @return
     */
    @GetMapping("myMail")
    public MyResult myMail(){
        return xnMealService.myMail();
    }

    /**
     * 绑定号码
     * @param phone
     * @return
     */
    @PostMapping("bindPhone")
    public MyResult bindPhone(String phone){
        if (phone==null || "".equals(phone) || "null".equals(phone)){
            return MyResult.failure("请选择号码");
        }
        return xnMealService.bindPhone(phone);
    }
}
