package com.usoft.sschool_student.serivice;

import com.usoft.smartschool.util.MyResult;

import java.util.Map;

public interface OpinionService {
    //查询班主任
    MyResult searchTeacher(Integer schoolId,Integer childId);
    //我要请假
    MyResult leave(Integer schoolId,Integer childId,Integer absentType, String beginDate, String endDate, String absentTime, String attr1);
    //查询我的请假信息
    MyResult searchMyLeave(Integer schoolId,Integer childId);
    //按月查询请假记录
    MyResult getMyTimeBook(String times);
}
