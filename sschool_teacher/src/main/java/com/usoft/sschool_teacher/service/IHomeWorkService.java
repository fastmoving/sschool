package com.usoft.sschool_teacher.service;

import com.usoft.smartschool.pojo.HlTeacher;
import com.usoft.smartschool.pojo.XnHomeworkManage;

import java.util.List;
import java.util.Map;


/**
 * @Author: 陈秋
 * @Date: 2019/5/6 16:05
 * @Version 1.0
 * 发布作业
 */
public interface IHomeWorkService {

    /**
     * 存入作业
     */
    int insertHomeWork(int teacherId,String hwName,int hwType,String acceptClass,String subject,
                       String expireTime,String hwContent,String hwContentImg);
    /**
    * 修改作业
    */
    int updateHomework(int hmwId,int thId,String hwName,int hwType,String acceptClass,String subject,
                       String expireTime,String hwContent,String hwContentImg);

    /**
     * 上传选择题
     * @param hwid
     * @param title
     * @param answerA
     * @param answerB
     * @param answerC
     * @param answerD
     * @param rightAnswer
     * @return
     */
    int insertHomeworkTitle(int hwid,String title,String answerA,String answerB,
                            String answerC,String answerD,int rightAnswer);

    /**
     * 选择科目
     * @param teacherId
     * @return
     */
    HlTeacher  getSubject(int teacherId);

    /**
     * 作业管理
     * @param teacherId
     * @return
     */
    int getStuHomeworkEsCount(int teacherId,int state,String stuName,String className,
                              String hwmName, String subject,String classId,int code);
    List<Map<String,Object>> getHomeworkmanager(int teacherId,int state,String stuName,String className,
                                                String hwmName,String start,String page,String subject,String classId,int code);

    /**
     * web作业管理
     * @param teacherId
     * @param hewName
     * @param type
     * @param classId
     * @param expireTime
     * @return
     */
    Map<String,Object> getHomeworkWeb(int teacherId, String hewName, String type,
                                          String classId, String expireTime, int page, int start) throws Exception;

    /**
     * 学生作业详情
     * @param homeworkId
     * @return
     */
    Map<String,Object> getHomeworkInformation(int homeworkId,int studentId);

    /**
     * 教师评语
     * @param imgPath
     * @param comment
     * @return
     */
    int insertComment(String imgPath,String comment,int teacherId,int stuId,int hwmId);
    //批量评语
    int insertComments(String imgPath,String comment,int teacherId,String stuHmwIds);

    /**
     * 作业详情
     */
    Map<String,Object> getHomeworkInformation(int hwmId);

    /**
     * 发布作业 web
     */
    int insertHomework(String hwName,Integer hwType,String acceptClass,String subject,
                       String expireTime,String hwContent,String hwContentImg,String array);

    /**
     * 发布作业选择班级
     */
    List getClasses(String subject);
}
