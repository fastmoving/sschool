package com.usoft.sschool_teacher.util;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @Author: 陈秋
 * @Date: 2019/6/20 13:54
 * @Version 1.0
 */
public class HttpContenUtil {
    /**
     * get方法访问
     *
     * @param urlStr
     * @return程序中访问http数据接口
     */
    public static JSONObject getURLGetContent(String urlStr) {
        /** 网络的url地址 */
        URL url = null;
        /** http连接 */
        // HttpURLConnection httpConn = null;
        /**//** 输入流 */
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {
            System.out.println("输入异常！");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                System.out.println("输出异常！");
            }
        }
        String result = sb.toString();
        JSONObject results = JSONObject.fromObject(result);
        // System.out.println(result);
        return results;
    }
}
