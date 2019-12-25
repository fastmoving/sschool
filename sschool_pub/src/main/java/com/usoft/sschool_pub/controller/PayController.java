package com.usoft.sschool_pub.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_pub.serivice.PayService;
import com.usoft.sschool_pub.util.SystemParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("pub/pay")
public class PayController extends BaseController{
    private static final Logger logger= LoggerFactory.getLogger(PayController.class);
    @Resource
    private PayService payService;

    /**
     * 积分支付
     * @param money
     * @param oid
     * @return
     */
    @PostMapping("/payByIntegral")
    public MyResult payByIntegral(Integer money, Integer oid){
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId = SystemParam.getUserId();
        return payService.payByIntegral(schoolId,userId,money,oid);
    }

    /**
     * 微信扫码支付
     * @param money
     * @param tradeNo
     * @param product_sys 购买的什么1 商品，2套餐，3视频,4兴趣班报名,5精品课堂
     * @return
     * @throws Exception
     */
    @PostMapping("/WXWebPay")
    public MyResult WXWebPay(Double money,Integer product_sys, String tradeNo,String attach) throws Exception {
        return payService.WXWebPay(money,product_sys,tradeNo,attach);
    }
    /**
     * 微信app支付
     * @param money
     * @param tradeNo
     * @param attach 购买的具体商品
     * @return
     * @throws Exception
     */
    @PostMapping("/WXAPPPay")
    public MyResult WXAPPPay(Double money,Integer product_sys, String tradeNo,String attach) throws Exception {
        return payService.WXAPPPay(money,product_sys,tradeNo,attach);
    }

    /**
     * 微信教师端app支付
     * @param money
     * @param product_sys
     * @param tradeNo
     * @param attach
     * @return
     * @throws Exception
     */
    @PostMapping("/WXAPPTeaPay")
    public MyResult WXAPPTeaPay(Double money,Integer product_sys, String tradeNo,String attach) throws Exception {
        return payService.WXAPPTeaPay(money,product_sys,tradeNo,attach);
    }
    /**
     * 微信支付回调地址
     * @param request
     * @param response
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/notifyl", method = RequestMethod.POST)
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) {
        payService.wxNotify(request,response);
    }

    /**
     * 微信支付查询订单
     * @param tradeNo
     * @return
     */
    @GetMapping("WXOrderquery")
    public MyResult Orderquery(String tradeNo,Integer payWay){
        return payService.Orderquery(tradeNo,payWay);
    }
    /**
     * 支付宝支付
     * @param type 1、app支付 2、web支付
     * @param tradeNo  交易号
     * @param product_sys 购买的什么1 商品，2套餐，3视频,4兴趣班报名,5精品课堂
     * @param attach 具体物品信息
     * @param returnUrl 回调地址
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/Alipay")
    public MyResult goAlipay(Integer type,Double money,String tradeNo,Integer product_sys,String attach,String returnUrl, HttpServletRequest request, HttpServletRequest response) throws Exception {
      return payService.payByAli(type,money,tradeNo,product_sys,attach,returnUrl,request,response);
    }

    /**
     * 支付宝教师端支付
     * @param money
     * @param tradeNo  交易号
     * @param product_sys 购买的什么1 商品，2套餐，3视频,4兴趣班报名,5精品课堂
     * @param attach 具体物品信息
     * @param returnUrl 回调地址
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/AliTeaPay")
    public MyResult goTeaAlipay(Double money,String tradeNo,Integer product_sys,String attach,String returnUrl, HttpServletRequest request, HttpServletRequest response) throws Exception {
        return payService.goTeaAlipay(money,tradeNo,product_sys,attach,returnUrl,request,response);
    }
    /**
     * 接收支付宝的同步返回的信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/alipayReturnNotice")
    public ModelAndView alipayReturnNotice(HttpServletRequest request, HttpServletRequest response)throws Exception{
        return payService.alipayReturnNotice(request, response);
    }

    /**
     * 接收支付宝的异步返回的信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alipayNotifyNotice")
    @ResponseBody
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception{
        return payService.alipayNotifyNotice(request, response);
    }
}
