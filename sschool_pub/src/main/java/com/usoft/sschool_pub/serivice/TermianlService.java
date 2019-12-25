package com.usoft.sschool_pub.serivice;

import com.usoft.smartschool.pojo.HlSchoolsevip;
import com.usoft.smartschool.util.MyResult;

/**
 * @author wlw
 * @data 2019/4/25 0025-17:33
 */
public interface TermianlService {
    //查询筛选条件
    MyResult conditions(Integer schoolId);
    //根据学校id查询班级
    MyResult searchClass(Integer schoolId);
    //查询老师信息
    MyResult allTeacherInfo(Integer schoolId,Integer classId);
    //添加关注
    MyResult addAttention(Integer videoId,Integer states);
    //取消关注
    MyResult deleteAttention(Integer videoId,Integer states);
    //查看关注
    MyResult myAttention(Integer states,Integer pageNo,Integer pageSize);
    //查询学校的所有直播
    MyResult searchVideo(Integer schoolId,Integer pageNo,Integer pageSize);
    //根据班级查询直播视频地址
    MyResult  classVideo(String classId,String schoolId,String conutyId,Integer pageNo,Integer pageSize);
    //用户选择试用
    MyResult addTestDays(Integer videoClassId,Integer videoSchoolId);
    //用户申请看视频权限
    MyResult videoCheck(Integer videoClassId,Integer videoSchoolId);
    //查询视频费用
    MyResult videoPrice(Integer type,Integer videoId,Integer videoClassId,Integer videoSchoolId);
    //保存精品课堂订单
    MyResult savePointFocusOrder(Integer videoId,String price);
    //保存视频订单
    MyResult saveVideoOrder(Integer type,Integer num,Integer videoId,Integer videoClassId,Integer videoSchoolId,String price);
    //查询所有的点播播视频信息
    MyResult allPrg(Integer schoolId,Integer pageNo,Integer pageSize);
    //筛选点播视频
    MyResult screenPrg(String classId, String schoolId, String conutyId,String teaId,String subId,String timeId, Integer pageNo, Integer pageSize);
    //直播、点播视频权限判断
    MyResult isRule(Integer videoId,Integer videoType,Integer videoSchoolId,Integer videoClassId);
    //名师讲堂筛选条件
    MyResult topqualityLevel(Integer schoolId,String subject1);
    //名师讲堂
    MyResult pointFocus(Integer schoolId,String subject,Integer teaId,Integer pageNo,Integer pageSize);
    //直播点播订单查询
    MyResult searchByOrder(String byuuid);
    //名师讲堂订单查询
    MyResult fineClassOrder(String byuuid);
    //改变订单状态
    MyResult changeOrderStatus(Integer orderType,String orderId);
    //我的已购课程
    MyResult myVideo(Integer pageNo,Integer pageSize);
    //ip地址列表
    MyResult sevip(Integer VideoSchoolId);
}
