package com.usoft.sschool_manage.controller.order;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.order.XnGoodsOrderCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品订单统计
 * @Author jijh
 * @Date 2019/5/21 17:00
 */
@RestController
@RequestMapping("manage/gocollect")
public class XnGoodsOrderCollectionController  extends BaseController {


    @Autowired
    private XnGoodsOrderCollectionService xnGoodsOrderCollectionService;


    /**
     * 总的订单统计
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("totalcollection")
    public Object totalCollection(String beginTime, String endTime){
        return xnGoodsOrderCollectionService.TotalOrderAndAveragePrice(beginTime, endTime);
    }


    /**
     * 订单状态统计
     * @param type
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("orderypteco")
    public Object OrderTypeCollection(Integer type,String beginTime, String endTime){
        return xnGoodsOrderCollectionService.OrderTypeCollection(type, beginTime, endTime);
    }


    /**
     * 年成交量统计
     * @param beginYear
     * @param endYear
     * @return
     */
    @GetMapping("yeardone")
    public Object yearDoneCollection(String beginYear, String endYear){
        return xnGoodsOrderCollectionService.YearDoneCollection(beginYear,endYear);
    }

    /**
     * 月成交量统计
     * @param year
     * @return
     */
    @GetMapping("monthdone")
    public Object monthDoneCollection(String year){
        return xnGoodsOrderCollectionService.MonthDoneCollection(year);
    }

    /**
     * 日成交量统计
     * @param month
     * @return
     */
    @GetMapping("daydone")
    public Object dayDoneCollection(String month){
        return xnGoodsOrderCollectionService.DaysDoneCollection(month);
    }

    /**
     * 小时成交量统计
     * @param day
     * @return
     */
    @GetMapping("hourdone")
    public Object hourDoneCollection(String day){
        return xnGoodsOrderCollectionService.hourDoneCollection(day);
    }

}
