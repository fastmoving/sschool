package com.usoft.sschool_pub.PayUtil;

public class AlipayConfig {

	public static String app_id = "2016092700607309";//在后台获取（必须配置）
	
	public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC16a0b/ZHPF9N65j42JD0xbB+c3znGVj5zjs3isasDA6KGBerCeViGamAEQpRAQQPbeUr2qPElhZrtsYQhbC88oNEwa+c+qtninQdnIirU6Oh+qQujXluTpSxrEtHBx84VF9zfHWfmXX6tboBkfecTU4XyvAB380NFlF22PQJo3ntsSCaPdXZWHa2bWodE4ZCY9mcvJwiMIwq56OaOeijrCcb2woFNdq1HaYlxlJlsSkkUYKZwrKuutOV8xyFFuQcBTCzzF/09GvW7GUjO1FEFJ/8rfgp6ua3RfTPCvGN2vlv++vimj1omRv7Lc+bHz4rJQSjvaUmqAjkhqq9B/kz/AgMBAAECggEBAK3oWJsivgtKjt22UZE28d1WrZ4t9bC78HZO9lsbWQ7Hoc8YxwbmNRBDj8li0RHgVcyy10yZ0/f4E1XbWCMPptUCNRpifa72bNhtII+jJWlnxCSafUrQQSIUTTOLyUXFOyoFJx2RoiukBB/PPSVVC9z5A1w8CQKjaBOTtEctailPsvgrjRaX8qlEg0oRSE0w7st2sf9y5QmDRg1AxEAdbJ/99hMDEBgJ75i6YT2azn64DIukLWptbIWJY7RBy1SJkZkmK1ldSx29uN4fBS29nGhCVDw+w2FYoByVCMvpJFqbsgyS0FghaWK+YMfA9jH8sNWY1wR6KoY2VJLt3dhdIUECgYEA51Y63P8CqVGpvO6HLtwqLa70/qdiv8emxkL5Z9sLRaiK2wsS43HAg+acU06XnJq0daLlJqbOIIMmwC20C/3yjgCKkItFKGm1lEcRmZPcwnKjWwv3Vv3sIYqY3b27LDBr+OLp0qnnCEBLnBbKO5mhoPnJZIhr8Xp44BhMJGw/ZOECgYEAyU6J57deDy1O3ZhfcMBhZpTpoDz9RQkMFHn692KqpHXZrOX5JMs3Xc+fQ7oNK/sw1M4hJCU0SubSKx7gz4oJzzGzBqfZOEqC/Uosa6QH+ELjH6o+7IcQFih2gcbXM9400XR57QtyGtOf4rxeuThXeQT1frKuljz52CeueC1mDd8CgYBl9c6J2ud8Yx7Rw7vXPnhknCklF/dhGYj690ffq16DfC5/LplLn7Y/LGQYH5qZDmKdOoePCpPtJoDs0Pf7FRlT1w87d/aqQjUivlQWgWIaXdZ76YFBWTc67VBE4zvc1Raxoql2DSZjr5rjl/PYYvUm8xD9e1dAHj+qXWsp3n/uYQKBgQDAO663RsSca6DPPW2OZzfb1MIQkY0t+oi9mQpOaLsZV6zpTacQOrsBa33kF+vyHuovnTPKp3h7OlZidRcrON9LK2ORLIHjHkxeAhHPLJGrwnvPRizWg9eZwUQBg9cHod8AUWEJOw/YjFQ3Gbi+2Q229ERWm9zafRn9D0+tVXUKMwKBgQCIKbPnuiITW0ShEz3n4RQge070vm6zYTqDFXvUHwJukK5/ey4iwue0W24/JLNzMb0XNaUerDgxT7g/cjluPl6WLJqqAOtF/CUS1GMxa7oBuOF5F8+qHvC8+n7pZcNrOgqYKFwh/XwXy58UmYrtY9Lk8Rhy05raXLORGJsP23gi4A==";//教程查看获取方式（必须配置）

	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqdcNma2FSPwPEe9NC54KF1HsIig/gDIANrl2ed5hcqN8Tq6L+KqGQz3TgwOtO+KBCi4S4ldo1EJRgkap1EBvxD2L3j8qlBlMhe8Yv8fdb22Qzy7HWp0pr6V4vyd5ZhVzR9WfkSh5kN80Zl7M6qXfa8/lbDzylGESAfaYFrrtsrE++FK4PCEmU+fCGAOMNc2VObZDUrHBXa0IN2dETynftUPrwbkxWdFLN5NJhLX8jxkfMyYq2x4F1o1NKpWNvkGs2lxUzNJgDMSN1NrJE0VwzHdnmFjSzFbqdFB7kEkEYx0ZQ9Nfsyx5SQ16R9VKBT4vwDWK+yXP3V8BU+FN6ARi0wIDAQAB";//教程查看获取方式（必须配置）
	
	public static String notify_url = "http://47.94.243.54:8880/pub/pub/pay/alipayNotifyNotice";
	
	public static String return_url = "http://baidu.com";
	
	public static String sign_type = "RSA2";
	
	public static String charset = "utf-8";
	
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";//注意：沙箱测试环境，正式环境为：https://openapi.alipay.com/gateway.do
}