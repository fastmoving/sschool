package com.usoft.sschool_student.serivice;

import com.usoft.smartschool.util.MyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wlw
 * @data 2019/4/24 0024-9:51
 */
public interface HomeWorkService {
    //查询所有作业
    MyResult serachAll(Integer stuId,Integer pageNo,Integer pageSize);
    //未做作业列表
    MyResult notdidHwk(Integer pageNo,Integer pageSize);
    //已做作业列表
    MyResult didHwk(Integer pageNo,Integer pageSize);
    //在线答题作业详情
    MyResult homeworkInfo(Integer hwId);
    //提交作业
    MyResult subHomework(Integer steId,Integer hwId,String ans);
    //上传答题图片
    MyResult addHomework(HttpServletRequest request, Integer hwId,Integer childId,String content);
    //查询已做的作业
    MyResult hasdidHomeWork(Integer hwId);
}
