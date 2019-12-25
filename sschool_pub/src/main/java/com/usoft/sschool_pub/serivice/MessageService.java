package com.usoft.sschool_pub.serivice;

import com.usoft.smartschool.util.MyResult;

public interface MessageService {
    //我的消息通知
    MyResult myNotice(Integer type,Integer schoolId);
    //改变消息状态为已读
    MyResult changeMessageState(Integer mid);
    //删除消息
    MyResult deleteMessage(Integer mid);
    //按消息类型删除消息
    MyResult deleteByType(Integer messageType);
    //通知详情
    MyResult messageInfo(Integer schoolId,Integer mid);
    //班级通讯录
    MyResult mailList(Integer classId,Integer type,Integer schoolId);
    //查询班级群聊消息
    MyResult classChat(Integer classId,Integer type,Integer schoolId,Integer pageNo,Integer pageSize);
    //发消息
    MyResult addMessage(Integer classId,String content,Integer type,Integer schoolId);
}
