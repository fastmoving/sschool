package com.usoft.sschool_pub.util.PayUtil;

import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.util.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 微信api 相关url
 * 
 * @author xty
 *
 */
/*@Service*/
public class WXUtil {
	private final static Logger log = LoggerFactory.getLogger(WXUtil.class);
	public static void main(String[] args) throws Exception {
		//wxUserPay("olwuL5ZUY7mHDYdep64gLdOUAmm4", "192.168.1.26", null, getByUUId());
//	System.out.println(base64StringToImage(getQRCode(1)));

//		System.out.println(getminiqrQr(1,1));
	}

	/**
	 * 用户端 获取微信access_token
	 *
	 * @param
	 */
	public static String getAccessToken() {
		String loginUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ WXConstants.User_AppID + "&secret=" + WXConstants.User_AppSecret;
		String s = HttpsClientUtil.httpGet(loginUrl);

		JSONObject obj = JSONObject.parseObject(s);
		if (obj.get("access_token") == null)
			return null;
		return obj.getString("access_token");
	}

	/**
	 * 用户端 获取微信登录授权
	 *
	 * @param code 临时登录凭证code
	 */
	public static String getUserOpenid(String code) {
		log.info(" 用户端 获取微信登录授权code值:"+code);
		String loginUrl = "https://api.weixin.qq.com/sns/jscode2session" + "?appid=" + WXConstants.User_AppID
				+ "&secret=" + WXConstants.User_AppSecret + "&js_code=" + code + "&grant_type="
				+ WXConstants.GRANT_TYPE;
		String s = HttpsClientUtil.httpGet(loginUrl);

		log.info(" 用户端 获取微信登录授权返回值:"+s);
		JSONObject obj = JSONObject.parseObject(s);
		if (obj.get("openid") == null)
			return null;
		return obj.getString("openid");
	}

	/**
	 * 微信发送付款通知
	 * @param touser
	 * @param template_id
	 * @param form_id
	 * @param data
	 * @return
	 */
	public static MyResult sendMessage(String touser,String template_id,String form_id,Object data) {
		String string = getAccessToken();
		Map map = new HashMap();
		//map.put("access_token",string);
		map.put("touser", touser);
		map.put("template_id", template_id);
		map.put("form_id", form_id);
		map.put("data",data);
		String xml = "";
		try {
			xml = WXPayUtil.mapToXml(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + string;
		String s = HttpsClientUtil.postXml(url, xml);
		log.info("发送https请求返回数据:" + s);
		// 解析返回数据
		try {
			Map<String, String> map1 = WXPayUtil.xmlToMap(s);
			if (map1.isEmpty() || !map1.get("errmsg").equals("ok")) {
				log.error("error={}", "微信消息调用失败,原因：" + map1.get("errmsg"));
				return MyResult.failure("微信消息调用失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MyResult.success("发送成功了");
	}
		/**
	 * 用户端 微信统一下单
	 *
	 * @param ip          ip地址
	 * @param money       总金额（分）
	 * @param tradeNo 	  订单号
	 * @throws Exception
	 */
	public static MyResult wxUserPay(String openId, String ip, Integer money, String tradeNo,Integer attach)
			throws Exception {
		// 取出需要参与签名参数组成预签名map
		Map<String, String> signMap = new HashMap<>();
		signMap.put("openid", openId);
		signMap.put("appid", WXConstants.User_AppID);
		signMap.put("mch_id", WXConstants.User_MCH_ID);
		String uuid = WXPayUtil.generateNonceStr();
		signMap.put("nonce_str", uuid);
		signMap.put("trade_type", WXConstants.JSAPI_Trade_Type);
		signMap.put("notify_url", WXConstants.User_NOTIFY_URL);
		signMap.put("spbill_create_ip", ip);
		signMap.put("out_trade_no", tradeNo);
		// signMap.put("total_fee",
		// String.valueOf(money.multiply(BigDecimal.valueOf(100)).intValue()));
		signMap.put("total_fee", "1");// 测试
		signMap.put("body", WXConstants.User_BODY);
		signMap.put("attach",attach.toString());
		// 得到微信sign签名
		// 封装成xml
		String xml = "";
		try {
			xml = WXPayUtil.generateSignedXml(signMap, WXConstants.User_WX_MCH_KEY);
		} catch (Exception e) {
			log.error("error={}", "封装XML数据错误");
			return MyResult.failure("封装XML数据错误");
		}

		// 发送https请求
		String response = HttpsClientUtil.postXml(WXConstants.PAY, xml);

		log.info("发送https请求返回数据:" + response);
		// 解析返回数据
		try {
			Map<String, String> map = WXPayUtil.xmlToMap(response);
			Map<String, String> resultMap = new HashMap<>();

			if (map.isEmpty() || "FAIL".equals(map.get("return_code"))) {
				log.error("error={}", "微信支付调用失败,原因：" + response);
				return MyResult.failure("微信支付调用失败");
			}
			if ("FAIL".equals(map.get("result_code"))){
				return MyResult.failure("微信支付调用结果失败了");
			}
			// 生成二次签名返回给客户端
			String prepay_id = map.get("prepay_id");
			String timeStamp = String.valueOf(new Date().getTime());
			resultMap.put("appId", WXConstants.User_AppID);
			resultMap.put("package", "prepay_id=" + prepay_id);
			resultMap.put("timeStamp", timeStamp);
			resultMap.put("nonceStr", uuid);
			resultMap.put("signType", "MD5");
			String resultSign = WXPayUtil.generateSignature(resultMap, WXConstants.User_WX_MCH_KEY);

			JSONObject obj = new JSONObject();
			obj.put("appId", WXConstants.User_AppID);
			obj.put("package", "prepay_id=" + prepay_id);
			obj.put("timeStamp", timeStamp);
			obj.put("nonceStr", uuid);
			obj.put("signType", "MD5");

			obj.put("paySign", resultSign);
			obj.put("out_trade_no", tradeNo);
			log.info("info={}", obj);

			obj.remove("appId");
			return MyResult.success(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.error("error={}", "微信支付异常");
		return MyResult.failure("微信支付异常");
	}

	/**
	 * 回调地址里的信息判断
	 * @param stringBuffer
	 */
	public void notifyUtil(StringBuffer stringBuffer){
		//转换为map
		Map<String, String> resultMap = null;

		try {
			resultMap = WXPayUtil.xmlToMap(stringBuffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//判断签名是否正确，必须包含sign字段，否则返回false。
		boolean flag = false;
		try {
			flag = WXPayUtil.isSignatureValid(resultMap, WXConstants.User_WX_MCH_KEY, WXConstants.SignType.MD5);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (flag) {
			log.info("notify 签名验证 成功通过");
			if (resultMap.get("return_code").equals("SUCCESS") && resultMap.get("result_code").equals("SUCCESS")) {
				log.info("订单支付已成功");
				//商户订单号
				String out_trade_no = resultMap.get("out_trade_no");
				//公众账号ID
				String appid = resultMap.get("appid");
				//付款银行
				String bank_type = resultMap.get("bank_type");
				//现金支付金额
				String cash_fee = resultMap.get("cash_fee");
				//是否关注公众账号
				String is_subscribe = resultMap.get("is_subscribe");
				//商户号
				String mch_id = resultMap.get("mch_id");
				//随机字符串
				String nonce_str = resultMap.get("nonce_str");
				//用户标识
				String openid = resultMap.get("openid");
				//业务结果
				String result_code = resultMap.get("result_code");
				//签名
				String sign = resultMap.get("sign");
				//支付完成时间
				String time_end = resultMap.get("time_end");
				//订单金额
				String total_fee = resultMap.get("total_fee");
				//交易类型
				String trade_type = resultMap.get("trade_type");
				//微信支付订单号
				String transaction_id = resultMap.get("transaction_id");
			}
		}
	}

	/**
	 * 微信支付回调成功后返回参数
	 * 
	 * @return
	 */
	public static String returnXML() {
		Map<String, String> signMap = new HashMap<>();
		signMap.put("return_code", "SUCCESS");
		signMap.put("return_msg", "OK");

		// 封装成xml
		String xml = "";
		try {
			xml = WXPayUtil.mapToXml(signMap);
			log.info("微信支付回调成功后xml格式:" + xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

	/**
	 * 生成订单号
	 * 
	 * @return
	 */
	public static String getByUUId() {
		/*int machineId = 20;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("ss");
		String time = format.format(date);
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {// 有可能是负数
			hashCodeV = -hashCodeV;
		}

		String uuid = String.format("%04d", hashCodeV);
		uuid = uuid.substring(uuid.length() - 1, uuid.length());

		String random = String.format("%04d", new Random().nextInt(99999999));
		random = random.substring(random.length() - 1, random.length());
		return machineId + time + uuid + random;*/

		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String newDate=sdf.format(new Date());
		String result="";
		Random random=new Random();
		for(int i=0;i<3;i++){
		result+=random.nextInt(10);
		}
		return newDate+result;
	}
}
