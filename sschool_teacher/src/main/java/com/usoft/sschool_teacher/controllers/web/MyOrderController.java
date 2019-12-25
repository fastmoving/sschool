package com.usoft.sschool_teacher.controllers.web;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.service.ITeacherMyselfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: 陈秋
 * @Date: 2019/5/22 11:06
 * @Version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher/order")
public class MyOrderController {

    @Autowired
    private ITeacherMyselfService myselfService;

    /**
     * 我的订单
     * @return
     */
    @RequestMapping("/getMyOrder")
    public MyResult getMyOrder(String pageSize,String currentPage){
        Integer page = 0;
        Integer start = 0;
        if (pageSize==null || "".equals(pageSize))page=10;
        else page = Integer.parseInt(pageSize.trim());
        if (currentPage==null || "".equals(currentPage))start=1;
        else start = Integer.parseInt(currentPage.trim());
        List<Map> resData = myselfService.getMyOrder(start,page);
        Integer resSum = myselfService.getMyOrderCount();
        if (resData==null || resData.size()==0){
            return new MyResult(2,"没有数据","");
        }
        return new MyResult(1,"success",resData,0,0,0,resSum);
    }

    /**
     * 订单详情
     * @return
     */
    @RequestMapping("/orderIfo")
    public MyResult getOrderIfo(String orderId){
        int ordId = 0;
        try {
            ordId = Integer.parseInt(orderId.trim());
        }catch (Exception e){
            return new MyResult(2,"上传的ID格式不对","");
        }
        Map resData = myselfService.getOrderIfo(ordId);
        if (resData == null || resData.size()==0){
            return new MyResult(2,"没有数据","");
        }
        return new MyResult(1,"success",resData);
    }
}
