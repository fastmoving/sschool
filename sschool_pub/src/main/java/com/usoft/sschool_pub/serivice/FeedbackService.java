package com.usoft.sschool_pub.serivice;

import com.usoft.smartschool.util.MyResult;

public interface FeedbackService {
    //查询记录
    MyResult searchMsg();
    //意见反馈
    MyResult sendMsg(String content,Integer acpType,Integer acpId);
    //查看具体留言信息
    MyResult oneMeg(Integer acpType,Integer acpId);
    //删除
    MyResult delete(Integer acpType,Integer acpId );
}
