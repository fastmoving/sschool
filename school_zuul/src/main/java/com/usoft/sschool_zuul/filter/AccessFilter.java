package com.usoft.sschool_zuul.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.usoft.sschool_zuul.dao.*;
import com.usoft.sschool_zuul.entity.*;
import com.usoft.sschool_zuul.util.MyResult;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @Author jijh
 * @Date 2019/5/20 10:06
 */
public class AccessFilter  extends ZuulFilter {

    private String SECRET = "xnft6666";

    private static Map<String,Claim> map;


    @Resource
    private XnUrlDao xnUrlDao;

    @Resource
    private XnUrlLimitsDao xnUrlLimitsDao;


    @Resource
    private HlTeacherMapper hlTeacherMapper;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Map<String,Object> result  = new HashMap<>();
        result.put("status",999);
        result.put("message","无权限");
        Object json  = JSON.toJSON(result);
        String res = json.toString();
        //获取token
        String token = request.getParameter("token");
        if(token == null || "".equals(token)){
            token = request.getHeader("token");
        }
        System.out.println("zuul token:"+token);
        //获取请求路径
        String path = request.getRequestURI();
        List<XnUrl> xnUrls = xnUrlDao.select(new XnUrl());
        boolean isExist = false;
        Integer urlId = null;
        for(XnUrl xnUrl : xnUrls){
            String url = xnUrl.getUrl();
            if(path.contains(url)){
                isExist = true;
                urlId = xnUrl.getId();
            }
        }
        if(isExist){
            //解析token
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt);
            map = jwt.getClaims();
            //存入userId和roleId
            Claim userId = map.get("userId");
            Integer userIdInt = userId.asInt();
            Claim schoolId = map.get("schoolId");
            Integer schoolIdInt = schoolId.asInt();
            if(userIdInt == null || userIdInt ==0){
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(411);
                ctx.setResponseBody(res);
                ctx.getResponse().setContentType("application/json;utf-8");
            }
            HlTeacherKey hlTeacherKey = new HlTeacherKey(userIdInt,schoolIdInt);

            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(hlTeacherKey);
            if(hlTeacher == null){
                ctx.getResponse().setContentType("application/json;utf-8");
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(411);
                ctx.setResponseBody(res);
            }
            Integer roleIdInt = hlTeacher.getTtype();
            XnUrlLimits xnUrlLimits = new XnUrlLimits();
            xnUrlLimits.setRoleId(roleIdInt);
            List<XnUrlLimits> xnUrlLimitses = xnUrlLimitsDao.select(xnUrlLimits);
            if(xnUrlLimitses == null || xnUrlLimitses.size() < 1){
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(411);
                ctx.setResponseBody(res);
                ctx.getResponse().setContentType("application/json;utf-8");
            }else{
                boolean isExistAgain = false;
                  for(XnUrlLimits xnUrlLimits1 : xnUrlLimitses){
                        if(xnUrlLimits1.getUrl().equals(urlId)){
                            isExistAgain =true;
                        }
                  }
                  if(isExistAgain){
                      ctx.setSendZuulResponse(true);
                  }else{
                      ctx.getResponse().setContentType("application/json;utf-8");
                      ctx.setSendZuulResponse(false);
                      ctx.setResponseStatusCode(411);
                      ctx.setResponseBody(res);
                  }
            }
        }else{
            ctx.setSendZuulResponse(true);
//            try{
//                Object result = JSONObject.toJSON(MyResult.RightDenied("您没有权限"));
//                os.write(String.valueOf(result).getBytes("UTF-8"));
//                return MyResult.RightDenied("您没有权限");
//            }catch (IOException e){
//                e.printStackTrace();
//            }
        }
        return null;
    }
}
