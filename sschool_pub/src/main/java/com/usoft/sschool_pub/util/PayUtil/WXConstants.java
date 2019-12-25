package com.usoft.sschool_pub.util.PayUtil;

/**
 * 微信常量
 * 
 * @author xty
 *
 */
public class WXConstants {
	/**
	 * 用户端 微信Appid
	 */
	public static final String User_AppID = "wxe2bfb227831c9fe4";//wx8fd746f32febb65f

	/**
	 * 用户端 微信AppSecret
	 * eaef891d4e9fb9c351668b8239bf355c
	 */
	public static final String User_AppSecret = "8f2a05d44c7c751a07150f0fc9bc8afd";//8f2a05d44c7c751a07150f0fc9bc8afd
	/**
	 * 用户端 微信商户号
	 */
	public static final String User_MCH_ID = "1549078251";

	/**
	 * 用户端 微信商户支付 密匙
	 */
	public static final String User_WX_MCH_KEY = "0E68E0D32AmD03n69AB6F46907F9c25m";

	/**
	 * 微信请求类型
	 */
	public static final String GRANT_TYPE = "authorization_code";

	/**
	 * 微信支付类型
	 */
	//APP支付
	public static final String APP_Trade_Type = "APP";
	//小程序支付
	public static final String JSAPI_Trade_Type = "JSAPI";
	//扫码支付
	public static final String NATIVE_Trade_Type = "NATIVE";
	//H5支付
	public static final String MWEB_Trade_Type = "MWEB";

	/**
	 * 微信统一支付下单地址
	 */
	public static final String PAY = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 微信查询订单地址
	 */
	public static final String GET_ORDER = "https://api.mch.weixin.qq.com/pay/orderquery";

	/**
	 * 用户端——微信支付回调地址
	 */
	public static final String User_NOTIFY_URL ="http://47.104.124.97:8090/pub/pub/pay/notifyl";
	/**
	 *  微信商户支付 body 描述
	 */
	public static final String User_BODY = "智慧校园-支付";

	public static final String ip="47.104.124.97";
	/**
	 * 签名类型
	 * 
	 * @author Administrator
	 *
	 */
	public enum SignType {
		MD5, HMACSHA256
	}

	public static final String FIELD_SIGN = "sign";

}
