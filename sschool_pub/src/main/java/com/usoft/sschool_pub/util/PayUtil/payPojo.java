package com.usoft.sschool_pub.util.PayUtil;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
@Component
@PropertySource("classpath:config/pay.properties")
public class payPojo {

    private static String appId;

    private static String mchId;

    private static String mchKey;

    private static String teaAppId;

    private static String teaMchId;

    private static String teaMchKey;

    private static String wechatNotify_url;

    private static String AliAppId;

    private static String AliPrivateKey;

    private static String AliPublicKey;

    private static String AliNotify_url;

    private static String AliReturn_url;

    private static String AliTeaAppId;

    private static String AliTeaPrivateKey;

    private static String AliTeaPublicKey;

    public  String getTeaAppId() {
        return teaAppId;
    }
    @Value("${WechatPay.Tea.appId}")
    public  void setTeaAppId(String teaAppId) {
        payPojo.teaAppId = teaAppId;
    }

    public  String getTeaMchId() {
        return teaMchId;
    }
    @Value("${WechatPay.Tea.mchId}")
    public  void setTeaMchId(String teaMchId) {
        payPojo.teaMchId = teaMchId;
    }

    public  String getTeaMchKey() {
        return teaMchKey;
    }
    @Value("${WechatPay.Tea.mchKey}")
    public  void setTeaMchKey(String teaMchKey) {
        payPojo.teaMchKey = teaMchKey;
    }

    public  String getAliTeaAppId() {
        return AliTeaAppId;
    }
    @Value("${Ali.Tea.appid}")
    public  void setAliTeaAppId(String aliTeaAppId) {
        AliTeaAppId = aliTeaAppId;
    }

    public  String getAliTeaPrivateKey() {
        return AliTeaPrivateKey;
    }
    @Value("${Ali.Tea.private_key}")
    public  void setAliTeaPrivateKey(String aliTeaPrivateKey) {
        AliTeaPrivateKey = aliTeaPrivateKey;
    }

    public  String getAliTeaPublicKey() {
        return AliTeaPublicKey;
    }
    @Value("${Ali.Tea.public_key}")
    public  void setAliTeaPublicKey(String aliTeaPublicKey) {
        AliTeaPublicKey = aliTeaPublicKey;
    }

    public  String getWechatNotify_url() {
        return wechatNotify_url;
    }
    @Value("${WechatPay.notify_url}")
    public  void setWechatNotify_url(String wechatNotify_url) {
        payPojo.wechatNotify_url = wechatNotify_url;
    }

    public  String getAliNotify_url() {
        return AliNotify_url;
    }
    @Value("${Ali.notify_url}")
    public  void setAliNotify_url(String aliNotify_url) {
        AliNotify_url = aliNotify_url;
    }

    public  String getAliReturn_url() {
        return AliReturn_url;
    }
    @Value("${Ali.return_url}")
    public  void setAliReturn_url(String aliReturn_url) {
        AliReturn_url = aliReturn_url;
    }

    public  String getAliAppId() {
        return AliAppId;
    }

    @Value("${Ali.appid}")
    public  void setAliAppId(String aliAppId) {
        AliAppId = aliAppId;
    }

    public  String getAliPrivateKey() {
        return AliPrivateKey;
    }
    @Value("${Ali.private_key}")
    public  void setAliPrivateKey(String aliPrivateKey) {
        AliPrivateKey = aliPrivateKey;
    }

    public  String getAliPublicKey() {
        return AliPublicKey;
    }
    @Value("${Ali.public_key}")
    public  void setAliPublicKey(String aliPublicKey) {
        AliPublicKey = aliPublicKey;
    }

    public String getAppId() {
        return appId;
    }
    @Value("${WechatPay.appId}")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }
    @Value("${WechatPay.mchId}")
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }
    @Value("${WechatPay.mchKey}")
    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpConnectTimeoutMs() {
        return 6*1000;
    }

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpReadTimeoutMs() {
        return 8*1000;
    }

}
