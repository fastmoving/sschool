package com.usoft.sschool_pub.util;

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;



/**
 *@file 拦截器
 *@date 2018年7月25日
 *@author jijh
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private TokenUtil tokenUtil;
	
	private static Map<String,Claim> map;
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		  response.setHeader("Content-type", "text/html;charset=UTF-8");
		  response.setCharacterEncoding("UTF-8");
		  response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		  response.setHeader("Access-Control-Max-Age", "3600");
		  response.setHeader("Access-Control-Allow-Headers", "*");
		  response.setHeader("Access-Control-Allow-Credentials","true");
		  response.setHeader("Access-Control-Allow-Origin", "*");
		ServletOutputStream os = response.getOutputStream();
		String token  = request.getHeader("token");
		if(token==null) {
			token = request.getParameter("token");
		}
		System.out.println(token);
		if(ObjectUtil.isEmpty(token)) {
			Object result = JSONObject.toJSON(MyResult.outOfDate("您没有登录"));
			os.write(String.valueOf(result).getBytes("UTF-8"));
			return false;
		}
		//解决service为null无法注入问题 
		if (redisUtil == null) {
	         BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()); 
	         redisUtil = (RedisUtil) factory.getBean("redisUtil"); 
	      } 
		if (tokenUtil == null) {
	         BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()); 
	         tokenUtil = (TokenUtil) factory.getBean("tokenUtil"); 
	      } 
		
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(tokenUtil.SECRET)).build(); 
			DecodedJWT jwt = verifier.verify(token);
			System.out.println(jwt);
			map = jwt.getClaims();
			//存入userId和roleId
			Claim userId = map.get("userId");
			SystemParam.setUserId(userId.asInt());
			Claim roleId = map.get("roleId");
			SystemParam.setUserType(roleId.asInt());
			Claim schoolId = map.get("schoolId");
			SystemParam.setSchoolId(schoolId.asInt());
			Claim childId = map.get("childId");
			SystemParam.setChildId(childId.asInt());
			Claim types=map.get("types");
			SystemParam.setType(types.asInt());
			System.out.println("用户id："+SystemParam.getUserId());
			System.out.println("未用的type："+SystemParam.getUserType());
			System.out.println("学校id是："+SystemParam.getSchoolId());
			System.out.println("孩子id是："+SystemParam.getChildId());
			System.out.println("用户类型是："+SystemParam.getType());
			//区分家长和老师的token
			String s=null;
			if (SystemParam.getType()==1){
				s="stu";
			}if (SystemParam.getType()==2){
				s="tea";
			}
			if(!(redisUtil.exist(s+userId.asInt()+"token") && token.equals(redisUtil.get(s+userId.asInt()+"token")))) {
				Object result = JSONObject.toJSON(MyResult.outOfDate("该账号在其他地方登录，请重新登录"));
				os.write(String.valueOf(result).getBytes("UTF-8"));
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Object result = JSONObject.toJSON(MyResult.outOfDate("登录已过期，请重新登录"));
			os.write(String.valueOf(result).getBytes("UTF-8"));
			return false;
		}
       return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

}
