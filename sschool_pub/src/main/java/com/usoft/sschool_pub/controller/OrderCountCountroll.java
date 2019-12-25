package com.usoft.sschool_pub.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_pub.serivice.OrderCountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("pub/order")
public class OrderCountCountroll {
    @Resource
    private OrderCountService orderCountService;

    /**
     * 按月统计
     * @param time
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/count")
    public MyResult count(String time,Integer pageNo,Integer pageSize){
        return orderCountService.count(time,pageNo,pageSize);
    }

    /**
     * 下载excel
     * @param request
     * @param response
     */
    @ResponseBody
    @GetMapping("/downloadExcel")
    public void downloadExcel(String time,HttpServletRequest request, HttpServletResponse response){
        orderCountService.downloadExcel(time,request,response);
    }
}
