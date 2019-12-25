package com.usoft.sschool_pub.serivice;

import com.usoft.smartschool.util.MyResult;

import javax.servlet.http.HttpServletRequest;

public interface KindnessService {
    //查询所有校园爱心
    MyResult allKindness(Integer schoolId,Integer pageNo,Integer pageSize);
    //爱心企业，人士
    MyResult kindnessPersion(Integer schoolId,Byte type);
    //查看单个校园爱心详情
    MyResult kindnessMsg(Integer id);
    //爱心留言
    MyResult commentKindness(Integer kid,String content,Integer parentId);
    //查看爱心排行
    MyResult kindnessRanking(Integer schoolId,Byte types);
    //我要做爱心
    MyResult addMyKindness(Integer schoolId, Byte userType, HttpServletRequest request, String description, String phone, Integer goodsType);
    //我的爱心
    MyResult myKindness(Integer kindnessType,Integer schoolId,Byte userType,Integer pageNo,Integer pageSize);
    //修改爱心
    MyResult changeMyKindness(Integer kindnessId,String description,String phone,Integer goodsType);
    //完成爱心
    MyResult signMyKindness(Integer kindnessId);
    //删除爱心
    MyResult deleteKindness(Integer kindnessId);
}
