package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 教师业务层
 * @Author jijh
 * @Date 2019/4/25 16:26
 */
public interface HlTeacherService {

    /**
     * 教师基础信息列表
     * @param name 教师姓名
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    MyResult listHlTeacherBase(String name, Integer pageNo, Integer pageSize);

    /**
     * 通过教师id获取教师所教授的科目
     * @param teacherId
     * @return
     */
    MyResult listSubjectByTeacher(Integer teacherId);

    /**
     * 通过科目获取教师信息
     * @param name
     * @param subject
     * @param pageNo
     * @param pageSize
     * @return
     */
    MyResult listHlTeacherBySubject(String name, String subject, Integer pageNo, Integer pageSize);


    /**
     * 教师信息列表
     * @param name 教师名称
     * @param type 教师类型
     * @param subject 教授科目
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    MyResult listHlTeacher(String name, Integer type, String subject, Integer pageNo, Integer pageSize);

    /**
     * 新增或者修改教师信息
     * @param id 教师id 修改时需要
     * @param name 教师姓名
     * @param birthday 教师生日
     * @param sex 教师性别
     * @param type 教师类型
     * @param subject 教授科目
     * @param phone 电话号码
     * @param password 密码
     * @param idImg 证件照
     * @param faceImg 风采照
     * @return
     */
    MyResult addOrUpdateTeacher(Integer id, String name, String birthday, Integer sex, Integer type, String subject, String phone, String password,
                                String idImg, String faceImg);


    /**
     * 删除教师信息
     * @param ids
     * @return
     */
    MyResult deleteTeacher(Integer[] ids);


    MyResult editPassword(Integer id, String password);
}
