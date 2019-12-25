package com.usoft.sschool_student.serivice;

import com.usoft.smartschool.pojo.XnScore;
import com.usoft.smartschool.util.MyResult;

import java.util.List;

/**
 * @author wlw
 * @data 2019/4/23 0023-16:34
 */
public interface ScoreService {
    //查询学期和考试类型
    MyResult term();
    //考试名称
    MyResult testName(String term);
    //查询成绩
    MyResult oneStudent(String term, String testName);
}

