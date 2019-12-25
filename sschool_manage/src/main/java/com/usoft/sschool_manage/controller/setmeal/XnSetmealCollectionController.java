package com.usoft.sschool_manage.controller.setmeal;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.setmeal.XnSeatmealCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jijh
 * @Date 2019/5/15 14:49
 */
@RestController
@RequestMapping("manage/setmealcollection")
public class XnSetmealCollectionController  extends BaseController {

    @Autowired
    private XnSeatmealCollectionService xnSeatmealCollectionService;


    /**
     * 套餐统计 绑定人数， 订单数， 缴费总金额
     * @return
     */
    @GetMapping("gettotal")
    public Object getTotalCollection(){
        return xnSeatmealCollectionService.totalCollection();
    }

    /**
     * 绑定角色统计图
     * @return
     */
    @GetMapping("bindrole")
    public Object getBindRoleCollection(){
        return xnSeatmealCollectionService.roleBindingCollection();
    }

    /**
     * 订单统计图
     * @return
     */
    @GetMapping("ordernumber")
    public Object getOrderNumberCollection(){
        return xnSeatmealCollectionService.orderNumberCollection();
    }

    /**
     * 订单金额统计
     * @return
     */
    @GetMapping("orderprice")
    public Object getOrderPriceCollection(){
        return xnSeatmealCollectionService.orderPriceCollection();
    }

}
