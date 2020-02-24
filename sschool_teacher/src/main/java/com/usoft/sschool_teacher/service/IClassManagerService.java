package com.usoft.sschool_teacher.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: 陈秋
 * @Date: 2019/5/5 17:50
 * @Version 1.0
 * 班级管理
 */
public interface IClassManagerService {

    /**
     * 获取到管理班级
     * @param teacherId
     * @return
     */
    int getManagerClassAppCount(Integer teacherId);
    List<Map<String,Object>> getManagerClass(Integer teacherId,int page,int start);
    int getManagerClassCount(Integer teacherId);

    /**
     * 获取班级相册
     */
    List<Map> getClassPhotos(int classId);

    /**
     * 请假详情
     * @param absentId
     * @return
     */
    Map getAbsentIfo(int absentId);

    /**
     * 请假审核管理
     * @param teacherId
     * @param classId
     * @param start
     * @param page
     * @return
     */
    List getAbsent(int teacherId,String classId,String status,int start,int page);
    int getAbsentCount(int teacherId,String classId,String status,int start,int page);

    /**
     * 请假审批
     * @param absentId
     * @return
     */
    int updateAbsent(String[] absentId,int status);
    List<Integer> selectMangerClass(List list);

    /**
     * 班级圈审核
     */
    List<Map> getClassCircle(int classId,int page,int start);
    List<Map> getWebClassCircle(Integer classId,int page,int start,Integer status);
    Integer getClassCircle1Count(Integer classId,Integer status);
    int getClassCircleCount(int classId);

    /**
     * 班级圈详情
     * @param circleId
     * @return
     */
    List<Map> getCircleIfo(int circleId);
    Integer getCount(int circleId);

    /**
     * 审批班级圈
     */
    int updateCircle(String[] circleIds,int status);

    /**
     * 一键放学
     */
    int insertClassOver(String[] classIds,String message,int code,String schoolCode);
    List getManagerClass2(String currentPage,String pageSize);

    /**
     * 获取全校班级id
     */
    String[] getSchoolClassId(Integer schoolId);

    /**
     * 班级通知
     */
    int insertClassInform(String[] classIds,String message);

    /**
     * 班级考勤
     */
    Map getClassChecking(int classId);
}
