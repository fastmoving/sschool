package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 成绩管理
 * @Author jijh
 * @Date 2019/4/26 11:45
 */
public interface XnScoreService {


    /**
     * 成绩列表 (条件模糊查询)
     * @param studentName 学生姓名
     * @param term 学期
     * @param schoolId 学校id
     * @param gradeId 年级id
     * @param classId 班级id
     * @param testName 考试名称
     * @param subject 科目
     * @param score 分数
     * @param wrongNumber 错题数量
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    MyResult listXnScore(String studentName, String term, Integer schoolId, Integer gradeId, Integer classId,
                         String testName, String subject, String score, Integer wrongNumber,Integer pageNo, Integer pageSize);


    /**
     * 添加或者更新成绩
     * @param id 成绩id 修改时需要
     * @param studentName 学生姓名
     * @param studentId  学生id
     * @param schoolId 学校id
     * @param gradeId 年级id
     * @param classId 班级id
     * @param term  学期
     * @param testName 考试名称
     * @param subject 科目
     * @param score 分数
     * @param wrongNumber 错题数量
     * @return
     */
    MyResult addOrUpdateXnScore(Integer id, Integer studentId, String studentName, Integer schoolId,  Integer classId,
                                String term, String testName, String subject, String score, Integer wrongNumber);


    /**
     * 删除成绩
     * @param ids
     * @return
     */
    MyResult deleteXnScore(Integer[] ids);
}
