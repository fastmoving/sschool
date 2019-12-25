package com.usoft.sschool_pub.util.PayUtil;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HttpsClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpsClientUtil.class); // 日志记录
	/**
	 * httpPost
	 * 
	 * @param url
	 *            路径
	 * @param jsonParam
	 *            参数
	 * @return
	 */
	public static String httpPost(String url, JSONObject jsonParam) {
		return httpPost(url, jsonParam, false);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            参数
	 * @param noNeedResponse
	 *            不需要返回结果
	 * @return
	 */
	public static String httpPost(String url, JSONObject jsonParam,
			boolean noNeedResponse) {
		// post请求返回结果
		//DefaultHttpClient httpClient = new DefaultHttpClient();
		//替换DefaultHttpClient 
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost method = new HttpPost(url);
		try {
			if (null != jsonParam) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(jsonParam.toString(),
						"utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json;charset=UTF-8");
				method.setEntity(entity);
			}
			HttpResponse result = httpClient.execute(method);
			url = URLDecoder.decode(url, "UTF-8");
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == 200) {
				try {
					/** 读取服务器返回过来的json字符串数据 **/
					HttpEntity entity = result.getEntity();
					if (entity != null) {  
			            InputStream instreams = entity.getContent();  
			            String str = convertStreamToString(instreams);
			            method.abort();  
			            return str;
					}
					if (noNeedResponse) {
						return null;
					}
				} catch (Exception e) {
					logger.info("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.info("post请求提交失败:" + url, e);
		}
		return null;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public static String httpGet(String url) {
		// get请求返回结果
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = httpClient.execute(request);

			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				HttpEntity entity = response.getEntity();
				if (entity != null) {  
		            InputStream instreams = entity.getContent();  
		            String str = convertStreamToString(instreams);
		            request.abort();  
		            return str;
				}
			} else {
				logger.info("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.info("get请求提交失败:" + url, e);
		}
		return null;
	}
	
	  /**
     * 发送post xml格式
     *
     * @param url
     * @param xmlStr
     * @return
     */
    public static String postXml(String url, String xmlStr) {
        HttpsURLConnection urlCon = null;
        StringBuffer res = new StringBuffer();
        BufferedReader in = null;
        try {
            urlCon = (HttpsURLConnection) (new URL(url)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("Content-Length", String.valueOf(xmlStr.getBytes("UTF-8").length));
            urlCon.setUseCaches(false);
            urlCon.getOutputStream().write(xmlStr.getBytes("utf-8"));
            urlCon.getOutputStream().flush();
            urlCon.getOutputStream().close();

           in =new BufferedReader(new InputStreamReader(urlCon.getInputStream(),"UTF-8"));

            String line;
            while ((line = in.readLine()) != null) {
                res.append(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res.toString();
    }
	
	
	
	/**
	 * 处理乱码
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {    
        StringBuilder sb1 = new StringBuilder();    
        byte[] bytes = new byte[4096];  
        int size = 0;  
        
        try {    
        	while ((size = is.read(bytes)) > 0) {  
                String str = new String(bytes, 0, size, "UTF-8");  
                sb1.append(str);  
            }  
        } catch (IOException e) {    
            e.printStackTrace();    
        } finally {    
            try {    
                is.close();    
            } catch (IOException e) {    
               e.printStackTrace();    
            }    
        }    
        return sb1.toString();    
    }
}
