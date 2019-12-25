package com.usoft.sschool_pub.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 *@file token的创建
 *@date 2018年7月26日
 *@author jijh
 */
@Service
public class TokenUtil {
	
	@Autowired
	RedisUtil redisUtil;
	//密钥
	public  String SECRET="xnft6666";
	public  String createToken(Integer userId,Integer roleId,Integer schoolId,Integer childId,Integer userType) throws  Exception {
		//签发时间
		 Date authTime = new Date();
		 //过期时间
		 Calendar nowTime = Calendar.getInstance();
		 nowTime.add(Calendar.MINUTE, 43200);
		 Date expireDate = nowTime.getTime();
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("alg", "HS256");  
	     map.put("typ", "JWT"); 
	     String token = JWT.create()
	    		 .withHeader(map)
	    		 .withClaim("userId", userId)
	    		 .withClaim("roleId", roleId)
				 .withClaim("schoolId",schoolId)
				 .withClaim("childId",childId)
				 .withClaim("types", userType)
	    		 .withClaim("type", "token")
	    		 .withExpiresAt(expireDate)
	    		 .withIssuedAt(authTime)
	    		 .sign(Algorithm.HMAC256(SECRET));
	     //存入redis数据库
	     
	     //保存时间为1440分钟
		String s=null;
		if (userType==1){
			s="stu";
		}if (userType==2){
			s="tea";
		}
		redisUtil.set(s+userId+"token", token, 43200L);
		return token;
	}
	
	public  String createRefreshToken(Integer userId,Integer roleId) throws Exception{
		//签发时间
		 Date authTime = new Date();
		 //过期时间
		 Calendar nowTime = Calendar.getInstance();
		 nowTime.add(Calendar.MINUTE, 2);
		 Date expireDate = nowTime.getTime();
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("alg", "HS256");  
	     map.put("typ", "JWT"); 
	     String refreshToken = JWT.create()
	    		 .withHeader(map)
	    		 .withClaim("userId", userId)
	    		 .withClaim("roleId", roleId)
	    		 .withClaim("type", "refreshToken")
	    		 .withExpiresAt(expireDate)
	    		 .withIssuedAt(authTime)
	    		 .sign(Algorithm.HMAC256(SECRET));
	     return refreshToken;
	}
	
}
