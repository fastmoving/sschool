package com.usoft.sschool_pub.serivice;

import com.usoft.smartschool.util.MyResult;

/**
 * @author wlw
 * @data 2019/4/28 0028-16:28
 */
public interface CurriculumService {
    //学生课表
    MyResult stuCurri(Integer schoolId,Integer studentId,Integer week);
    //web端学生查询课表
    MyResult webStudentCurri();
    //教师查看课表
    MyResult teacherCurr(Integer schoolId);
}
