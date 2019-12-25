package com.usoft.sschool_pub.serivice.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.util.PayUtil.*;
import com.usoft.sschool_pub.exception.BaseException;
import com.usoft.sschool_pub.exception.ErrorCode;
import com.usoft.sschool_pub.mapper.*;
import com.usoft.sschool_pub.serivice.PayService;
import com.usoft.sschool_pub.util.QrCodeUtil;
import com.usoft.sschool_pub.util.SystemParam;
import com.usoft.sschool_pub.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Service("PayService")
public class PayServiceImpl implements PayService {
    private static final Logger logger= LoggerFactory.getLogger(PayServiceImpl.class);
    @Resource
    private XnGoodsOrderMapper xnGoodsOrderMapper;
    @Resource
    private XnTeacherIntegralMapper xnTeacherIntegralMapper;
    @Resource
    private XnSetmealOrderMapper xnSetmealOrderMapper;
    @Resource
    private XnStuFamilyinfoMapper xnStuFamilyinfoMapper;
    @Resource
    private XnIntrestEntryMapper xnIntrestEntryMapper;
    @Resource
    private XnVideoOrderMapper xnVideoOrderMapper;
    @Resource
    private XnFineclassOrderMapper xnFineclassOrderMapper;
    @Resource
    private XnOrderCountMapper xnOrderCountMapper;
    @Resource
    private SearchUtil searchUtil;
    @Resource
    private XnSetmealManageMapper xnSetmealManageMapper;
    @Resource
    private XnGoodsManageMapper xnGoodsManageMapper;
    @Resource
    private XnIntrestClassMapper xnIntrestClassMapper;
    @Resource
    private XnMailOrderMapper xnMailOrderMapper;
    @Autowired
    private payPojo payPojo;

    /**
     * 积分支付
     * @param schoolId
     * @param userId
     * @param money
     * @param oid
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MyResult payByIntegral(Integer schoolId, Integer userId,Integer money, Integer oid) {
        List<Map> list = searchUtil.teacherClass(schoolId, userId);
        if (ObjectUtil.isEmpty(list))return MyResult.failure("没找到您的信息");
        if ((Integer)list.get(0).get("totleScore")<money){
            return MyResult.failure("积分不足");
        }
        XnGoodsOrder order = xnGoodsOrderMapper.selectByPrimaryKey(oid);
        if (ObjectUtil.isEmpty(order))return MyResult.failure("没找到订单信息");
        order.setOrderstate((byte)2);
        order.setBuytime(new Date());
        int i1 = xnGoodsOrderMapper.updateByPrimaryKeySelective(order);
        if (i1!=1){
            return MyResult.failure("订单状态改变失败,请重试");
        }
        XnGoodsManage xnGoodsManage = xnGoodsManageMapper.selectByPrimaryKey(order.getGoodsid());
        if (ObjectUtil.isEmpty(xnGoodsManage) ){
            return MyResult.failure("没有找到该商品信息");
        }
        if (xnGoodsManage.getNumber()<order.getNumber()){
            return MyResult.failure("商品剩余数量小于订单数量，购买失败");
        }
        xnGoodsManage.setNumber(xnGoodsManage.getNumber()-order.getNumber());
        int i2 = xnGoodsManageMapper.updateByPrimaryKeySelective(xnGoodsManage);
        if (i2!=1){
            return MyResult.failure("订单状态改变失败,请重试");
        }
        XnTeacherIntegral xti=new XnTeacherIntegral();
        xti.setSid(schoolId);
        xti.setTid(userId);
        xti.setType((byte)2);
        xti.setNumber(-money);
        xti.setCreatetime(new Date());
        int i = xnTeacherIntegralMapper.insertSelective(xti);
        if (i!=1){
            return MyResult.failure("改变积分失败");
        }
        return MyResult.success("积分支付成功",xnGoodsOrderMapper.selectByPrimaryKey(oid));
    }

    /**
     * 根据订单号和物品分类查询订单信息
     * @param product_sys
     * @param tradeNo
     * @return
     */
    public Map<String,String> goodInfo(Integer product_sys,String tradeNo){
        Integer userId=0;
        Integer userType=0;
        Integer id=0;
        String money=null;
        Map<String,String> map=new HashMap<>();
        //获取订单信息
        switch (product_sys){
            //1、购买商品
            case 1:
                XnGoodsOrderExample example=new XnGoodsOrderExample();
                example.createCriteria().andOrdernumberEqualTo(tradeNo);
                List<XnGoodsOrder> xnGoodsOrders = xnGoodsOrderMapper.selectByExample(example);
                if (!ObjectUtil.isEmpty(xnGoodsOrders)){
                    userId=xnGoodsOrders.get(0).getUid();
                    userType= Integer.valueOf(xnGoodsOrders.get(0).getAttr1());
                    money=xnGoodsOrders.get(0).getTotalprice().toString();
                    id=xnGoodsOrders.get(0).getId();
                }
                break;
            //2、购买套餐
            case 2:
                XnMailOrderExample example1=new XnMailOrderExample();
                example1.createCriteria().andTradenoEqualTo(tradeNo);
                List<XnMailOrder> xnMailOrders = xnMailOrderMapper.selectByExample(example1);
                if (!ObjectUtil.isEmpty(xnMailOrders)){
                    userId=xnMailOrders.get(0).getUserid();
                    userType=xnMailOrders.get(0).getUserType();
                    money= String.valueOf(xnMailOrders.get(0).getPrice());
                    id=xnMailOrders.get(0).getId();
                }
                break;
             //3、视频权限
            case 3:
                XnVideoOrderExample example3=new XnVideoOrderExample();
                example3.createCriteria().andOrdernumberEqualTo(tradeNo);
                List<XnVideoOrder> xnVideoOrders = xnVideoOrderMapper.selectByExample(example3);
                if (!ObjectUtil.isEmpty(xnVideoOrders)){
                    userId=xnVideoOrders.get(0).getUid();
                    userType=xnVideoOrders.get(0).getUsertype();
                    money=xnVideoOrders.get(0).getAttr2();
                    id=xnVideoOrders.get(0).getId();
                }
                break;
            //4、兴趣班报名
            case 4:
                XnIntrestEntryExample example4=new XnIntrestEntryExample();
                example4.createCriteria().andAttr2EqualTo(tradeNo);
                List<XnIntrestEntry> xnIntrestEntries = xnIntrestEntryMapper.selectByExample(example4);
                if (!ObjectUtil.isEmpty(xnIntrestEntries)){
                    userId=xnIntrestEntries.get(0).getStuid();
                    userType=1;
                    money=xnIntrestEntries.get(0).getAttr3();
                    id=xnIntrestEntries.get(0).getId();
                }
                break;
            //5、精品课堂
            case 5:
                XnFineclassOrderExample example5=new XnFineclassOrderExample();
                example5.createCriteria().andAttr3EqualTo(tradeNo);
                List<XnFineclassOrder> xnFineclassOrders = xnFineclassOrderMapper.selectByExample(example5);
                if (!ObjectUtil.isEmpty(xnFineclassOrders)){
                    userId=xnFineclassOrders.get(0).getUserid();
                    userType= Integer.valueOf(xnFineclassOrders.get(0).getUsertype());
                    money=xnFineclassOrders.get(0).getAttr2();
                    id=xnFineclassOrders.get(0).getId();
                }
                break;
        }
        map.put("userId",userId.toString());
        map.put("userType",userType.toString());
        map.put("money",money);
        map.put("id",id.toString());
        return map;
    }
    /**
     * 微信扫码支付
     * @param money
     * @param tradeNo
     * @return
     */
    @Override
    public MyResult WXWebPay(Double money,Integer product_sys, String tradeNo,String attach) throws Exception {
        Map<String, String> stringObjectMap = goodInfo(product_sys, tradeNo);
        if (stringObjectMap.get("userId").equals("0")){
            return MyResult.failure("没找到订单信息");
        }
        if ((int)(Double.valueOf(stringObjectMap.get("money"))*100)!=(int)(money*100)){
            return MyResult.failure("金额与订单不符");
        }
        Map<String,String> signMap=new HashMap<>();
        signMap.put("appid", payPojo.getAppId());
        signMap.put("mch_id", payPojo.getMchId());
        String uuid = WXPayUtil.generateNonceStr();
        signMap.put("nonce_str", uuid);
        signMap.put("trade_type", WXConstants.NATIVE_Trade_Type);
        signMap.put("notify_url", payPojo.getWechatNotify_url());
        signMap.put("spbill_create_ip",  WXConstants.ip);
        signMap.put("product_id", String.valueOf(stringObjectMap.get("id")));
        signMap.put("out_trade_no", tradeNo);
        signMap.put("total_fee", "1");// 测试
        signMap.put("body", WXConstants.User_BODY);
        signMap.put("attach",attach);
        // 得到微信sign签名
        // 封装成xml
        String xml = "";
        try {
            xml = WXPayUtil.generateSignedXml(signMap, payPojo.getMchKey());
        } catch (Exception e) {
            return MyResult.failure("封装XML数据错误");
        }
        String response = HttpsClientUtil.postXml(WXConstants.PAY, xml);
        System.out.println("发送https请求返回数据:" + response);
        //解析返回的数据
        Map<String, String> map = WXPayUtil.xmlToMap(response);
        Map<String, String> resultMap = new HashMap<>();
        System.out.println("resultMap"+resultMap);
        if (map.isEmpty() || map.get("return_code").equals("FAIL")) {
            System.out.println("微信支付调用失败,原因：" + response);
            return MyResult.failure(map.get("return_msg")+",请稍后再试");
        }
        if ("FAIL".equals(map.get("result_code"))){
            return MyResult.failure(map.get("err_code_des")+",请稍后再试");
        }
        System.out.println("trade_type"+map.get("trade_type"));
        System.out.println("prepay_id"+map.get("prepay_id"));
        System.out.println("code_url"+map.get("code_url"));
        return MyResult.success("订单生产成功",map.get("code_url"));
    }

    /**
     * 微信app支付
     * @param money
     * @param tradeNo
     * @param attach
     * @return
     */
    @Override
    public MyResult WXAPPPay(Double money,Integer product_sys, String tradeNo, String attach) {
        Map<String, String> stringObjectMap = goodInfo(product_sys, tradeNo);

        if (stringObjectMap.get("userId").equals("0")){
            return MyResult.failure("没找到订单信息");
        }
        if ((int)(Double.valueOf(stringObjectMap.get("money"))*100)!=(int)(money*100)){
            return MyResult.failure("金额与订单不符");
        }
        // 参与签名参数组成预签名map
        Map<String, String> signMap = new HashMap<>();
        signMap.put("appid", payPojo.getAppId());
        signMap.put("mch_id", payPojo.getMchId());
        String uuid = WXPayUtil.generateNonceStr();
        signMap.put("nonce_str", uuid);
        signMap.put("trade_type", WXConstants.APP_Trade_Type);
        signMap.put("notify_url", payPojo.getWechatNotify_url());
        signMap.put("spbill_create_ip", WXConstants.ip);
        signMap.put("out_trade_no", tradeNo);
        signMap.put("total_fee", "1");// 测试
        signMap.put("body", WXConstants.User_BODY);
        signMap.put("attach",attach);
        String s1=null;
        try {
            s1 = WXPayUtil.generateSignature(signMap, payPojo.getMchKey(), WXConstants.SignType.MD5);
        } catch (Exception e) {
            logger.info("获取签名错误");
            e.printStackTrace();
        }
        signMap.put("sign",s1);
        // 得到微信sign签名

        String s2=null;
        try {
             s2 = WXPayUtil.mapToXml(signMap);
        } catch (Exception e) {
            logger.info("转换XML数据错误");
            e.printStackTrace();

        }
        String s = HttpsClientUtil.postXml(WXConstants.PAY, s2);
        System.out.println("发送https请求返回数据:" + s);
        //解析返回的参数
        try {
            Map<String, String> map = WXPayUtil.xmlToMap(s);
            Map<String, String> resultMap = new HashMap<>();
            if (map.isEmpty() || "FAIL".equals(map.get("return_code"))) {
                System.out.println("微信支付调用失败,原因：" +s );
                return MyResult.failure(map.get("return_msg")+",请稍后再试");
            }
            if ("FAIL".equals(map.get("result_code"))){
                logger.info(map.get("err_code_des"));
                return MyResult.failure(map.get("err_code_des")+",请稍后再试");
            }
            // 生成二次签名返回给客户端

            resultMap.put("appid", map.get("appid"));
            resultMap.put("partnerid", map.get("mch_id"));
            resultMap.put("prepayid", map.get("prepay_id"));
            resultMap.put("noncestr", map.get("nonce_str"));
            resultMap.put("timestamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
            resultMap.put("package", "Sign=WXPay");
            String resultSign = WXPayUtil.generateSignature(resultMap, payPojo.getMchKey(), WXConstants.SignType.MD5);
            resultMap.put("sign", resultSign);
            return MyResult.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return MyResult.failure("解析返回的数据错误");
        }
    }

    /**
     * 微信教师端app支付
     * @param money
     * @param product_sys
     * @param tradeNo
     * @param attach
     * @return
     */
    @Override
    public MyResult WXAPPTeaPay(Double money, Integer product_sys, String tradeNo, String attach) {
        Map<String, String> stringObjectMap = goodInfo(product_sys, tradeNo);

        if (stringObjectMap.get("userId").equals("0")){
            return MyResult.failure("没找到订单信息");
        }
        if ((int)(Double.valueOf(stringObjectMap.get("money"))*100)!=(int)(money*100)){
            return MyResult.failure("金额与订单不符");
        }
        // 参与签名参数组成预签名map
        Map<String, String> signMap = new HashMap<>();
        signMap.put("appid", payPojo.getTeaAppId());
        signMap.put("mch_id", payPojo.getTeaMchId());
        String uuid = WXPayUtil.generateNonceStr();
        signMap.put("nonce_str", uuid);
        signMap.put("trade_type", WXConstants.APP_Trade_Type);
        signMap.put("notify_url", payPojo.getWechatNotify_url());
        signMap.put("spbill_create_ip", WXConstants.ip);
        signMap.put("out_trade_no", tradeNo);
        signMap.put("total_fee", "1");// 测试
        signMap.put("body", WXConstants.User_BODY);
        signMap.put("attach",attach);
        String s1=null;
        try {
            s1 = WXPayUtil.generateSignature(signMap, payPojo.getTeaMchKey(), WXConstants.SignType.MD5);
        } catch (Exception e) {
            logger.info("获取签名错误");
            e.printStackTrace();
        }
        signMap.put("sign",s1);
        // 得到微信sign签名

        String s2=null;
        try {
            s2 = WXPayUtil.mapToXml(signMap);
        } catch (Exception e) {
            logger.info("转换XML数据错误");
            e.printStackTrace();

        }
        String s = HttpsClientUtil.postXml(WXConstants.PAY, s2);
        System.out.println("发送https请求返回数据:" + s);
        //解析返回的参数
        try {
            Map<String, String> map = WXPayUtil.xmlToMap(s);
            Map<String, String> resultMap = new HashMap<>();
            if (map.isEmpty() || "FAIL".equals(map.get("return_code"))) {
                System.out.println("微信支付调用失败,原因：" +s );
                return MyResult.failure(map.get("return_msg")+",请稍后再试");
            }
            if ("FAIL".equals(map.get("result_code"))){
                logger.info(map.get("err_code_des"));
                return MyResult.failure(map.get("err_code_des")+",请稍后再试");
            }
            // 生成二次签名返回给客户端

            resultMap.put("appid", map.get("appid"));
            resultMap.put("partnerid", map.get("mch_id"));
            resultMap.put("prepayid", map.get("prepay_id"));
            resultMap.put("noncestr", map.get("nonce_str"));
            resultMap.put("timestamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
            resultMap.put("package", "Sign=WXPay");
            String resultSign = WXPayUtil.generateSignature(resultMap, payPojo.getTeaMchKey(), WXConstants.SignType.MD5);
            resultMap.put("sign", resultSign);
            return MyResult.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return MyResult.failure("解析返回的数据错误");
        }
    }

    /**
     * 微信回调地址
     * @param request
     * @param response
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) {
        //1.从request获取XML流  转化成为MAP 数据
        logger.info("notify ssssssssss");
        logger.info("notify 成功被调用");
        System.out.println("notify xxxxxxxxx");
        try {
            StringBuffer stringBuffer = new StringBuffer();
            InputStream inputStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String temp;
            while ((temp = reader.readLine()) != null) {
                stringBuffer.append(temp);
            }
            reader.close();
            inputStream.close();
            //转换为map
            Map<String, String> stringMap = WXPayUtil.xmlToMap(stringBuffer.toString());
            //判断签名是否正确，必须包含sign字段，否则返回false。
            boolean flag = WXPayUtil.isSignatureValid(stringMap, payPojo.getMchKey(), WXConstants.SignType.MD5);
            if (flag) {
                logger.info("notify 签名验证 成功通过");
                logger.info("返回的数据："+stringMap);
                if (stringMap.get("return_code").equals("SUCCESS")) {
                    logger.info("订单支付已成功");
                    String tradeNo=stringMap.get("out_trade_no");
                    char c=tradeNo.charAt(tradeNo.length()-1);
                    //获取订单金额
                    Integer s = Integer.valueOf(stringMap.get("total_fee"))/100;
                    BigDecimal money=new BigDecimal(s);
                    //附加信息
                    String attch=stringMap.get("attach");
                    MyResult wirtes = wirtes(tradeNo, money, attch, 1);

                    if (wirtes.getStatus()==1){
                        logger.info("修改订单状态成功");
                        logger.info("成功返回数据");
                        Map<String, String> returnMap = new HashMap<>();
                        returnMap.put("return_code", "SUCCESS");
                        returnMap.put("return_msg", "SUCESS");
                        try {
                            String respXml = WXPayUtil.generateSignedXml(returnMap,payPojo.getMchKey());
                            response.getWriter().write(respXml);
                            response.getWriter().flush();
                            response.getWriter().close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        throw new Exception("处理信息失败");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信支付查询订单
     * @param tradeNo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public MyResult Orderquery(String tradeNo,Integer payWay) {
        if (payWay==2){
            return ALOrderquery(tradeNo,payWay);
        }else {
            Map<String,String> map=new HashMap<>();
            payPojo payPojo=new payPojo();
            map.put("appid",payPojo.getAppId());
            map.put("mch_id",payPojo.getMchId());
            map.put("out_trade_no",tradeNo);
            String uuid = WXPayUtil.generateNonceStr();
            map.put("nonce_str",uuid);
            String xml = "";
            try {
                xml = WXPayUtil.generateSignedXml(map, payPojo.getMchKey());
            } catch (Exception e) {
                e.printStackTrace();
                return MyResult.failure("封装XML数据错误");
            }
            String s = HttpsClientUtil.postXml(WXConstants.GET_ORDER, xml);
            System.out.println("查询订单返回数据:" + s);
            //解析返回的参数
            try {
                Map<String,String> stringMap=WXPayUtil.xmlToMap(s);
                if (stringMap.get("return_code").equals("SUCCESS")){
                    if (stringMap.get("result_code").equals("SUCCESS")){
                        if (stringMap.get("trade_state").equals("SUCCESS")){
                            logger.info("订单支付已成功");
                            //获取订单金额
                            Integer s1 = Integer.valueOf(stringMap.get("total_fee"))/100;
                            BigDecimal money=new BigDecimal(s1);
                            //附加信息
                            String attch=stringMap.get("attach");
                            MyResult wirtes = wirtes(tradeNo, money, attch, 1);
                            if (wirtes.getStatus()==1){
                                Map m=new HashMap();
                                m.put("total_fee",Double.parseDouble(stringMap.get("total_fee"))/100);
                                m.put("transaction_id",stringMap.get("transaction_id"));
                                m.put("out_trade_no",stringMap.get("out_trade_no"));
                                XnOrderCountExample example=new XnOrderCountExample();
                                example.createCriteria().andAttr2EqualTo(tradeNo);
                                List<XnOrderCount> xnOrderCounts = xnOrderCountMapper.selectByExample(example);
                                m.put("payWay",1);
                                m.put("orderId",wirtes.getData());
                                m.put("attach",ObjectUtil.isEmpty(xnOrderCounts)?null:xnOrderCounts.get(0).getProduct());
                                m.put("time_end",ObjectUtil.isEmpty(xnOrderCounts)?null:TimeUtil.TimeToYYYYMMDDHHMMSS(xnOrderCounts.get(0).getCreateTime()));
                                return MyResult.success(m);
                            }else {
                                return wirtes;
                            }
                        }else {
                            return MyResult.failure(stringMap.get("trade_state_desc"));
                        }
                    }else {
                        return MyResult.failure(stringMap.get("err_code_des"));
                    }
                }else {
                    return MyResult.failure(stringMap.get("return_msg"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return MyResult.failure("解析返回的数据错误");
            }
        }
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
    @Override
    public MyResult payByAli(Integer type,Double money,String tradeNo,Integer product_sys,String attach,String returnUrl, HttpServletRequest request, HttpServletRequest response){
        //数据库查询订单信息
        Map<String, String> stringObjectMap = goodInfo(product_sys, tradeNo);
        if (stringObjectMap.get("userId").equals("0")){
            return MyResult.failure("没找到订单信息");
        }
        if ((int)(Double.valueOf(stringObjectMap.get("money"))*100)!=(int)(money*100)){
            return MyResult.failure("金额与订单不符");
        }
        //返回的数据
        String result = null;
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, payPojo.getAliAppId() ,payPojo.getAliPrivateKey(), "json", AlipayConfig.charset,payPojo.getAliPublicKey(), AlipayConfig.sign_type);
        //设置请求参数
        if (type==1){
            AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();

            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody(WXConstants.User_BODY);
            model.setSubject(attach);
            model.setOutTradeNo(tradeNo);
            model.setTimeoutExpress("30m");
            model.setTotalAmount("0.01");
            model.setProductCode("QUICK_MSECURITY_PAY");
            alipayRequest.setBizModel(model);
            alipayRequest.setReturnUrl("www.baidu.com");
            alipayRequest.setNotifyUrl(payPojo.getAliNotify_url());
            //请求
            try {
                result=alipayClient.sdkExecute(alipayRequest).getBody();
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }else {
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

            /*if (returnUrl==null){
                alipayRequest.setReturnUrl(payPojo.getAliReturn_url());
            }else {
                alipayRequest.setReturnUrl(returnUrl);
            }*/

            AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
            // 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
            model.setTimeoutExpress("30m");
            model.setBody(WXConstants.User_BODY);
            model.setSubject(attach);
            model.setOutTradeNo(tradeNo);
            model.setTotalAmount("0.01");
            //必传固定参数
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            alipayRequest.setNotifyUrl(payPojo.getAliNotify_url());
            alipayRequest.setBizModel(model);
            logger.info("notifyUrl是:"+AlipayConfig.notify_url);
            //请求
            try {
                result = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }
        return MyResult.success("下单成功",result);
    }

    /**
     * 支付宝教师端支付
     * @param money
     * @param tradeNo
     * @param product_sys
     * @param attach
     * @param returnUrl
     * @param request
     * @param response
     * @return
     */
    @Override
    public MyResult goTeaAlipay(Double money, String tradeNo, Integer product_sys, String attach, String returnUrl, HttpServletRequest request, HttpServletRequest response) {
        //数据库查询订单信息
        Map<String, String> stringObjectMap = goodInfo(product_sys, tradeNo);
        if (stringObjectMap.get("userId").equals("0")){
            return MyResult.failure("没找到订单信息");
        }
        if ((int)(Double.valueOf(stringObjectMap.get("money"))*100)!=(int)(money*100)){
            return MyResult.failure("金额与订单不符");
        }
        //返回的数据
        String result = null;
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, payPojo.getAliTeaAppId() ,payPojo.getAliTeaPrivateKey(), "json", AlipayConfig.charset,payPojo.getAliTeaPublicKey(), AlipayConfig.sign_type);
        //设置请求参数

        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();

        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(WXConstants.User_BODY);
        model.setSubject(attach);
        model.setOutTradeNo(tradeNo);
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        alipayRequest.setBizModel(model);
        alipayRequest.setReturnUrl("www.baidu.com");
        alipayRequest.setNotifyUrl(payPojo.getAliNotify_url());
        //请求
        try {
            result=alipayClient.sdkExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return MyResult.success("下单成功",result);
    }

    /**
     * 接收支付宝的同步返回的信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public ModelAndView alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
        logger.info("支付成功, 进入同步通知接口...");
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        ModelAndView mv = new ModelAndView("alipaySuccess");
        //验签成功逻辑处理
        if (signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
            //商品信息
            String body = new String(request.getParameter("body").getBytes("ISO-8859-1"), "UTF-8");

            logger.info("********************** 支付成功(支付宝同步通知) **********************");
            logger.info("* 订单号: {}", out_trade_no);
            logger.info("* 支付宝交易号: {}", trade_no);
            logger.info("* 实付金额: {}", total_amount);
            logger.info("* 购买产品: {}", body);
            logger.info("***************************************************************");

            mv.addObject("out_trade_no", out_trade_no);
            mv.addObject("trade_no", trade_no);
            mv.addObject("total_amount", total_amount);
            mv.addObject("productName", body);
        } else {
            logger.info("支付, 验签失败...");

        }
        return mv;
    }

    /**
     * 接收支付宝的异步返回的信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
        logger.info("支付成功, 进入异步通知接口...");
        System.out.println("Success Pay");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        System.out.println(params);
        //支付宝验签
        boolean signVerified = AlipaySignature.rsaCheckV1(params, payPojo.getAliPublicKey(), AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        if (signVerified) {//验证成功
            //商户订单号
            String tradeNo = new String(request.getParameter("out_trade_no"));
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no"));
            //交易状态
            String trade_status = new String(request.getParameter("trade_status"));
            //付款金额
            String money = new String(request.getParameter("total_amount"));
            //订单名称
            String body = new String(request.getParameter("body"));
            //附加信息
            String attch=new String(request.getParameter("subject"));
            //商户号
            String app_id=new String(request.getParameter("app_id"));
            if (!payPojo.getAliAppId().equals(app_id)){
                System.out.println("appid不正确！");
                return "failure";
            }
            //改变订单状态
            if (trade_status.equals("TRADE_SUCCESS")) {
                //逻辑处理
                MyResult wirtes = wirtes(tradeNo, new BigDecimal(money), attch, 2);
                if (wirtes.getStatus()==1){
                    System.out.println("success");
                    return "success";
                }else {
                    System.out.println(wirtes.getMessage());
                    return "failure";
                }
            } else {//验证失败
                logger.info("支付失败...");
                return "failure";
            }
        }else {
            System.out.println("支付返回的数据验签失败"+"failure");
            return "failure";
        }
    }

    /**
     * 支付宝支付订单状态查询
     * @param tradeNo
     * @return
     */
    public MyResult ALOrderquery(String tradeNo,Integer payWay) {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.searchURL,payPojo.getAliAppId(), payPojo.getAliPrivateKey(),"json","UTF-8",payPojo.getAliPublicKey(),"RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{\"out_trade_no\":\""+tradeNo+"\" }");
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("该订单已支付成功");
            MyResult wirtes = wirtes(tradeNo, new BigDecimal(response.getTotalAmount()), response.getSubject(), payWay);
            if (wirtes.getStatus()==1){
                Map m=new HashMap();
                m.put("transaction_id",response.getTradeNo());
                m.put("total_fee",response.getTotalAmount());
                m.put("out_trade_no",response.getOutTradeNo());
                m.put("payWay",payWay);
                m.put("orderId",wirtes.getData());
                XnOrderCountExample example=new XnOrderCountExample();
                example.createCriteria().andAttr2EqualTo(tradeNo);
                List<XnOrderCount> xnOrderCounts = xnOrderCountMapper.selectByExample(example);
                m.put("attach",ObjectUtil.isEmpty(xnOrderCounts)?null:xnOrderCounts.get(0).getProduct());
                m.put("time_end",ObjectUtil.isEmpty(xnOrderCounts)?null:TimeUtil.TimeToYYYYMMDDHHMMSS(xnOrderCounts.get(0).getCreateTime()));
                return MyResult.success("该订单已支付成功成功",m);
            }else {
                return wirtes;
            }

        } else {
            System.out.println("调用失败");
            return MyResult.failure(response.getSubMsg());
        }

    }



    /**
     * 修改商品订单状态
     * @param tradeNo
     * @return
     */
    public MyResult changeOrderState(BigDecimal money,String tradeNo,Integer payWay,String attch) {
        XnGoodsOrderExample example=new XnGoodsOrderExample();
        example.createCriteria().andOrdernumberEqualTo(tradeNo);
        List<XnGoodsOrder> xnGoodsOrders = xnGoodsOrderMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnGoodsOrders))return MyResult.failure("没有找到订单号");
        XnGoodsOrder order = xnGoodsOrders.get(0);

        /*if (!order.getTotalprice().equals(money)){
            return MyResult.failure("支付金额与订单金额不符");
        }*/
        if (order.getOrderstate()==2){
            return MyResult.success(order.getId());
        }
        order.setOrderstate((byte)2);
        order.setAttr3(payWay.toString());
        order.setBuytime(new Date());
        int i = xnGoodsOrderMapper.updateByPrimaryKeySelective(order);
        //减少数量
        XnGoodsManage xnGoodsManage = xnGoodsManageMapper.selectByPrimaryKey(xnGoodsOrders.get(0).getGoodsid());
        xnGoodsManage.setNumber(xnGoodsManage.getNumber()-xnGoodsOrders.get(0).getNumber());
        int i1 = xnGoodsManageMapper.updateByPrimaryKeySelective(xnGoodsManage);
        MyResult myResult = orderCount(order.getTotalprice(), payWay,attch, 1, tradeNo);
        if (i!=1 || i1!=1 || myResult.getStatus()!=1){
            return MyResult.failure("改变订单状态失败");
        }
        return MyResult.success(order.getId());
    }


    public MyResult stemealOrderState(BigDecimal money,String tradeNo,Integer payWay,String attch) {
        Integer userId = SystemParam.getUserId();
        //改变订单状态
        XnSetmealOrderExample example=new XnSetmealOrderExample();
        example.createCriteria().andOrdernumberEqualTo(tradeNo);
        example.setOrderByClause("id desc");
        List<XnSetmealOrder> xnSetmealOrders = xnSetmealOrderMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnSetmealOrders))return MyResult.failure("没找到订单信息");
        /*
        if (!xnSetmealOrders.get(0).getOrderprice().equals(money)){
            return MyResult.failure("支付金额与订单金额不符");
        }*/
        if (xnSetmealOrders.get(0).getOrderstate()==2){
            //用户表添加订单id
            XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(userId);
            xnStuFamilyinfo.setOid(xnSetmealOrders.get(0).getId());
            int i1 = xnStuFamilyinfoMapper.updateByPrimaryKeySelective(xnStuFamilyinfo);
            logger.info("orderState=2");
            return MyResult.success(xnSetmealOrders.get(0).getId());
        }
        logger.info("orderState=1");
        xnSetmealOrders.get(0).setBuytime(new Date());
        xnSetmealOrders.get(0).setPaytype("1");
        xnSetmealOrders.get(0).setOrderstate((byte)2);
        int i = xnSetmealOrderMapper.updateByPrimaryKeySelective(xnSetmealOrders.get(0));
        //用户表添加订单id
        XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(userId);
        xnStuFamilyinfo.setOid(xnSetmealOrders.get(0).getId());
        int i1 = xnStuFamilyinfoMapper.updateByPrimaryKeySelective(xnStuFamilyinfo);
        XnStuFamilyinfoExample example1=new XnStuFamilyinfoExample();
        example1.createCriteria().andStuidEqualTo(SystemParam.getChildId()).andPhoneNotEqualTo(xnStuFamilyinfo.getPhone());
        List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example1);
        XnSetmealManage xnSetmealManage = xnSetmealManageMapper.selectByPrimaryKey(xnSetmealOrders.get(0).getSetid());
        if (!ObjectUtil.isEmpty(xnStuFamilyinfos)){
            for(XnStuFamilyinfo xs:xnStuFamilyinfos){
                try {
                    xs.setExpiretime(TimeUtil.YYYYMMDDToTime(xnSetmealManage.getEnddate()));
                    int i2 = xnStuFamilyinfoMapper.updateByPrimaryKeySelective(xs);
                    if (i2!=1){
                        throw new RuntimeException("时间转换失败");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        MyResult myResult = orderCount(xnSetmealOrders.get(0).getOrderprice(), payWay,attch, 2, tradeNo);
        if (i!=1|| i1!=1 || myResult.getStatus()!=1){
            logger.info("orderState=faile");
            return MyResult.failure("改变失败");
        }
        logger.info("orderState=success");
        return MyResult.success(xnSetmealOrders.get(0).getId());
    }

    /**
     * 改变套餐订单状态
     * @param tradeNo
     * @return
     */
    public MyResult mailOrder(BigDecimal money,String tradeNo,Integer payWay,String attch){
        XnMailOrderExample example=new XnMailOrderExample();
        example.createCriteria().andTradenoEqualTo(tradeNo);
        List<XnMailOrder> xnMailOrders = xnMailOrderMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnMailOrders))return MyResult.failure("没找到该订单信息");
        /*if (!xnMailOrders.get(0).getPrice().equals(money)){
            return MyResult.failure("支付金额与订单金额不符");
        }*/
        if (xnMailOrders.get(0).getOrderStatus().equals(2)){
            return MyResult.success(xnMailOrders.get(0).getId());
        }
        Integer month=null;
        if (xnMailOrders.get(0).getTimeType()==1){
            month=xnMailOrders.get(0).getNumber()*12;
        }else {
            month=xnMailOrders.get(0).getNumber();
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,+month);
        //修改订单状态，添加支付时间
        xnMailOrders.get(0).setOrderStatus(2);
        xnMailOrders.get(0).setPayTime(new Date());
        xnMailOrders.get(0).setAttr1(TimeUtil.TimeToYYYYMMDDHHMMSS(calendar.getTime()));
        int i = xnMailOrderMapper.updateByPrimaryKeySelective(xnMailOrders.get(0));
        //给购买和绑定账号修改过期时间
        if (xnMailOrders.get(0).getUserType()==1){
            XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(xnMailOrders.get(0).getUserid());
            xnStuFamilyinfo.setOid(xnMailOrders.get(0).getId());
            try {
                xnStuFamilyinfo.setExpiretime(TimeUtil.YYYYMMDDHHMMSSToTime(xnMailOrders.get(0).getAttr1()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            xnStuFamilyinfoMapper.updateByPrimaryKeySelective(xnStuFamilyinfo);
        }
        MyResult myResult = orderCount(xnMailOrders.get(0).getPrice(), payWay,attch, 2, tradeNo);
        if (i!=1 || myResult.getStatus()!=1){
            logger.info("orderState=faile");
            return MyResult.failure("改变失败");
        }
        logger.info("orderState=success");
        return MyResult.success(xnMailOrders.get(0).getId());
    }

    /**
     * 兴趣班报名表改变状态
     * @param tradeNo
     * @return
     */
    public MyResult changeOrder(BigDecimal money,String tradeNo,Integer payWay,String attch) {
        XnIntrestEntryExample example=new XnIntrestEntryExample();
        example.createCriteria().andAttr2EqualTo(tradeNo);
        List<XnIntrestEntry> xnIntrestEntries = xnIntrestEntryMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnIntrestEntries))return MyResult.failure("没找到该订单信息");
        XnIntrestEntry xnIntrestEntry = xnIntrestEntries.get(0);
        /*
        if (!xnIntrestEntries.get(0).getAttr3().equals(money)){
            return MyResult.failure("支付金额与订单金额不符");
        }*/
        if (xnIntrestEntries.get(0).getAttr1().equals("2")){
            return MyResult.success(xnIntrestEntry.getId());
        }
        xnIntrestEntry.setAttr1("2");
        int i = xnIntrestEntryMapper.updateByPrimaryKeySelective(xnIntrestEntry);
        MyResult myResult = orderCount(new BigDecimal(xnIntrestEntries.get(0).getAttr3()), payWay, attch, 4, tradeNo);
        if (i!=1 || myResult.getStatus()!=1){
            return MyResult.failure("保存失败");
        }
        return MyResult.success(xnIntrestEntry.getId());
    }

    /**
     * 直播点播和精品课堂改变订单状态
     * @param orderType
     * @param tradeNo
     * @return
     */
    public MyResult changeOrderStatus(BigDecimal money,Integer orderType, String tradeNo,Integer payWay,String attch) {
        int i=0;
        int oid=0;
        MyResult myResult=null;
        if (orderType==1){
            XnVideoOrderExample example=new XnVideoOrderExample();
            example.createCriteria().andOrdernumberEqualTo(tradeNo);
            List<XnVideoOrder> xnVideoOrders = xnVideoOrderMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnVideoOrders))return MyResult.failure("没找到该订单信息");
            XnVideoOrder xnVideoOrder =xnVideoOrders.get(0);
            oid=xnVideoOrder.getId();
            /*
            if (!xnVideoOrder.getAttr2().equals(money)){
                return MyResult.failure("支付金额与订单金额不符");
            }*/
            if (xnVideoOrder.getAttr1().equals("2")){
                return MyResult.success(oid);
            }
            xnVideoOrder.setBuytime(new Date());
            xnVideoOrder.setAttr1("2");
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.MONTH, Integer.parseInt(xnVideoOrder.getAttr3()));
            Date time = calendar.getTime();
            xnVideoOrder.setAttr3(TimeUtil.TimeToYYYYMMDDHHMMSS(time));
            i=xnVideoOrderMapper.updateByPrimaryKeySelective(xnVideoOrder);
            myResult=orderCount(new BigDecimal(xnVideoOrder.getAttr2()),payWay,attch,3,tradeNo);
        }else {
            XnFineclassOrderExample example=new XnFineclassOrderExample();
            example.createCriteria().andAttr3EqualTo(tradeNo);
            List<XnFineclassOrder> xnFineclassOrders = xnFineclassOrderMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnFineclassOrders))return MyResult.failure("没找到该订单信息");
            XnFineclassOrder xnFineclassOrder =xnFineclassOrders.get(0);
            oid=xnFineclassOrder.getId();
            /*
            if (!xnFineclassOrder.getAttr2().equals(money)){
                return MyResult.failure("支付金额与订单金额不符");
            }*/
            if (xnFineclassOrder.getAttr1().equals("2")){
                return MyResult.success(oid);
            }
            xnFineclassOrder.setBuytime(new Date());
            xnFineclassOrder.setAttr1("2");
            i=xnFineclassOrderMapper.updateByPrimaryKeySelective(xnFineclassOrder);
            myResult = orderCount(new BigDecimal(xnFineclassOrder.getAttr2()), payWay, attch, 5, tradeNo);
        }
        if (i!=1 || myResult.getStatus()!=1){
            return MyResult.failure("改变订单状态失败");
        }
        return MyResult.success(oid);
    }

    /**
     * 写入统计表
     * @param price
     * @param pay_way
     * @param product   具体产品
     * @param product_sys 产品分类 1 商品，2套餐，3视频,4兴趣班报名,5精品课堂
     * @return
     */
    public MyResult orderCount(BigDecimal price, Integer pay_way, String product, Integer product_sys,String tradeNo) {
        Map<String, String> stringObjectMap1 = goodInfo(product_sys, tradeNo);
        if (stringObjectMap1.get("userId").equals("0")){
            return MyResult.failure("没找到订单信息");
        }
        XnOrderCountExample example=new XnOrderCountExample();
        example.createCriteria().andPayWayEqualTo(pay_way).andAttr2EqualTo(tradeNo);
        List<XnOrderCount> xnOrderCounts = xnOrderCountMapper.selectByExample(example);
        if (!ObjectUtil.isEmpty(xnOrderCounts)){
            return MyResult.success("已经保存了");
        }
        Map<String, String> stringObjectMap = searchUtil.setOrderCount(Integer.parseInt( stringObjectMap1.get("userId")),Integer.parseInt(stringObjectMap1.get("userType")));
        XnOrderCount xoc=new XnOrderCount();
        xoc.setPrice(price);
        xoc.setPayWay(pay_way);
        xoc.setProduct(product);
        xoc.setProductSys(product_sys.toString());
        xoc.setUserid(stringObjectMap.get("userId"));
        xoc.setUsertype(Integer.parseInt(stringObjectMap.get("userType")));
        xoc.setSchoolId(stringObjectMap.get("schoolId"));
        xoc.setCountyId(stringObjectMap.get("countyId"));
        xoc.setTownsId(stringObjectMap.get("townsId"));
        xoc.setPhone(stringObjectMap.get("phone"));
        xoc.setClassId(stringObjectMap.get("classId"));
        xoc.setCreateTime(new Date());
        xoc.setAttr1(stringObjectMap.get("gradeId"));
        xoc.setAttr2(tradeNo);
        int insert = xnOrderCountMapper.insert(xoc);
        if (insert!=1){
            return MyResult.failure("保存统计信息失败");
        }
        return MyResult.success("保存统计信息成功");
    }

    /**
     * 订单逻辑处理
     * @param tradeNo
     * @param money
     * @param attch
     * @param payWay
     * @return
     */
    public MyResult wirtes(String tradeNo,BigDecimal money,String attch,Integer payWay){
        String str=tradeNo.substring(tradeNo.length()-1);
        MyResult myResult=null;
        if ("a".equals(str)){
            myResult = changeOrderState(money, tradeNo, payWay,attch);
        }
        if ("b".equals(str)){
            myResult=mailOrder(money,tradeNo,payWay,attch);
        }
        if ("c".equals(str)){
            myResult=changeOrderStatus(money,1,tradeNo,payWay,attch);
        }
        if ("d".equals(str)){
            myResult=changeOrder(money,tradeNo,payWay,attch);
        }
        if ("e".equals(str)){
            myResult=changeOrderStatus(money,2,tradeNo,payWay,attch);
        }
        return myResult;
    }
}
