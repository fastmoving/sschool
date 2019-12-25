package com.usoft.sschool_manage.controller.order;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.order.XnGoodsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品订单
 * @Author jijh
 * @Date 2019/5/21 16:57
 */
@RestController
@RequestMapping("manage/goodsorder")
public class XnGoodsOrderController  extends BaseController {

    @Autowired
    private XnGoodsOrderService xnGoodsOrderService;

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
    @GetMapping("list")
    public Object listXnGoodsOrder(Integer type, String userName, Integer orderState, String buyTimeBegin, String buyTimeEnd, String priceBegin,
                                   String priceEnd, String phone, String orderNumber,Integer pageNo, Integer pageSize){
        return xnGoodsOrderService.listGoodsOrder(type, userName, orderState, buyTimeBegin, buyTimeEnd, priceBegin, priceEnd, phone, orderNumber, pageNo, pageSize);
    }

    /**
     * 查看订单详情
     * @param id
     * @return
     */
    @GetMapping("select")
    public Object selectXnGoodsOrder(Integer id){
        return xnGoodsOrderService.selectGoodsOrder(id);
    }
}
