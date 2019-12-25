package com.usoft.sschool_manage.service.live;

import com.usoft.smartschool.util.MyResult;

import java.util.List;

public interface XnCheckNumService {
    /**
     * 查询直播、点播总点击量
     */
    MyResult totalNum();
    //列表查询
    MyResult list(Integer classId, String stuName, String phone,Integer pageNo,Integer pageSize);

    //精品课堂订单列表
    MyResult fineList(Integer classId,String stuName,String phone,Integer pageNo,Integer pageSize);
    /**
     * 直播排序
     * @param videoType 视频类型 1、直播 2、点播
     * @return
     */
    MyResult liveRank(String time,Integer videoType);

    /**
     * 按周统计
     * @param start
     * @param end
     * @param videoType
     * @return
     */
    MyResult RankByWeek(String start,String end,Integer videoType);

    /**
     * 授权学生观看视频
     * @param stuId
     * @param videoType
     * @return
     */
    MyResult EmpVideo(Integer stuId, Integer cid, Integer videoType,String list, Integer payType);

    /**
     * 查询班级学生信息
     * @param sid
     * @param cid
     * @return
     */
    MyResult allStu(Integer sid,Integer cid);

    /**
     * 精品课堂列表
     * @return
     */
    MyResult fineClassList(Integer pageNo,Integer pageSize);
}
