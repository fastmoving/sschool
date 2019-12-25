package com.usoft.sschool_manage.controller.setmeal;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.setmeal.XnSetmealOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jijh
 * @Date 2019/5/13 17:04
 */

@RestController
@RequestMapping("manage/setorder")
public class XnSetOrderController  extends BaseController {

    @Autowired
    private XnSetmealOrderService xnSetmealOrderService;

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
    @GetMapping("list")
    public Object listXnSetOrder(String studentName, String phone, String orderTimeBegin, String orderTimeEnd,
                                 String priceBegin, String priceEnd, Integer setMealId, String ordernumber,
                                 Integer orderState,Integer pageNo,Integer pageSize){
        return xnSetmealOrderService.listSetmealOrder(studentName,phone,orderTimeBegin,orderTimeEnd,priceBegin,priceEnd,setMealId,ordernumber,orderState,pageNo,pageSize);
    }


    /**
     * 查询订单详情
     * @param orderId 订单ID
     * @return
     */
    @GetMapping("select")
    public Object selectXnSetOrder(Integer orderId){
        return xnSetmealOrderService.selectSetmealOrder(orderId);
    }

    /**
     * 绑定关系
     * @param orderId 订单id
     * @return
     */
    @GetMapping("selectdetail")
    public Object selectBindDetail(Integer orderId){
        return xnSetmealOrderService.listBindingDetail(orderId);
    }

}
