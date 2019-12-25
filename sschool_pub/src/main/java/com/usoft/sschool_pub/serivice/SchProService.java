package com.usoft.sschool_pub.serivice;

import com.usoft.smartschool.pojo.CfDepartment;
import com.usoft.smartschool.pojo.XnAnnounce;
import com.usoft.smartschool.pojo.XnIntrestClass;
import com.usoft.smartschool.pojo.XnTeaApperance;
import com.usoft.smartschool.util.MyResult;

import java.util.List;

/**
 * @author wlw
 * @data 2019/4/24 0024-15:18
 */
public interface SchProService {
    //轮播图
    MyResult carousel(Integer schoolId,Byte type,Byte position);
    //学校简介
    MyResult schoolInfo(Integer schoolId);
    //学校公告
    MyResult announce(Integer schoolId,Integer pageNo,Integer pageSize);
    //公告详情
    MyResult announceInfo(Integer aid);
    //教师风采
    MyResult teacherApperance(Integer schoolId,Integer pageNo,Integer pageSize);
    //兴趣班
    MyResult intClass(Integer schoolId,Integer pageNo,Integer pageSize);
    //兴趣班详情
    MyResult intrestClassInfo(Integer intrestClassId,Integer schoolId);
    //查询聊天记录
    MyResult communicate(Integer IntrestId);
    //沟通
    MyResult addcommunicate(Integer IntrestId,String content,Integer schoolId,Integer childId);
    //教师查询本人的兴趣班
    MyResult teaInterstId();
    //教师回复家长的聊天
    MyResult teaReply(Integer IntrestId,Integer recevieId,String content);
    //教师查看兴趣班沟通列表
    MyResult teaMsgList();
    //教师查看和单个家长的聊天记录
    MyResult teaSearchChat(Integer IntrestId,Integer userId );
    //报名信息写入数据库
    MyResult enterClass(Integer schoolId,Integer childId,Integer entryType,Integer icid);
    //兴趣班根据订单id查询订单详情
    MyResult searchOrder(Integer orderId);
    //支付成功后改变订单状态
    MyResult changeOrder(Integer ieid);

}
