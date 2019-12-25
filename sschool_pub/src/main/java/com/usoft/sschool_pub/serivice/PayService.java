package com.usoft.sschool_pub.serivice;

import com.alipay.api.AlipayApiException;
import com.usoft.smartschool.util.MyResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface PayService {
    //积分支付
    MyResult payByIntegral(Integer schoolId,Integer userId,Integer money, Integer oid);
    //微信扫码支付
    MyResult WXWebPay(Double money,Integer product_sys, String tradeNo,String attach) throws Exception;
    //微信app支付
    MyResult WXAPPPay(Double money,Integer product_sys, String tradeNo,String attach);
    //微信教师端app支付
    MyResult WXAPPTeaPay(Double money,Integer product_sys, String tradeNo,String attach);
    //微信支付回调
    void wxNotify(HttpServletRequest request, HttpServletResponse response);
    //支付订单查询
    MyResult Orderquery(String tradeNo,Integer payWay);
    //支付宝支付
    MyResult payByAli(Integer type,Double money,String tradeNo,Integer product_sys,String attach,String returnUrl, HttpServletRequest request, HttpServletRequest response);
   //教师端支付宝支付
    MyResult goTeaAlipay(Double money,String tradeNo,Integer product_sys,String attach,String returnUrl, HttpServletRequest request, HttpServletRequest response);
    //接收支付宝的同步返回的信息
    ModelAndView alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) throws Exception;
    //接收支付宝的异步返回的信息
    String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception;
    //支付宝支付订单状态查询
   /* MyResult ALOrderquery(String tradeNo);*/
}
