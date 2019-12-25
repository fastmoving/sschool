package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 学生业务层
 * @Author jijh
 * @Date 2019/4/26 11:26
 */
public interface HlStudentInfoService {


    /**
     * 学生基础信息列表
     * @param cid 班级id
     * @param studentName 学生姓名
     * @param pageNo
     * @param pageSize
     * @return
     */
    MyResult listStuInfoBase(Integer cid, String studentName,Integer pageNo, Integer pageSize);

    /**
     * 查看学生基本信息，返回年级，家长号码
     * @param sid
     * @return
     */
    MyResult selectStudentInfo(Integer sid);

    /**
     * 学生列表信息
     * @param studentName 学生姓名
     * @param gradeType 年级
     * @param classId 班级id
     * @param parentPhone 家长号码
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    MyResult listHlStudentInfo(String studentName, Integer gradeType, Integer classId, String parentPhone,
                               Integer pageNo, Integer pageSize);

    /**
     * 添加或者修改学生信息
     * @param id
     * @param name
     * @param schoolId
     * @param gradeId
     * @param classId
     * @param birthday
     * @param sex
     * @param parentRole
     * @param parentPhone
     * @param idImg
     * @param faceImg
     * @return
     */
    MyResult addOrUpdateHlStudent(Integer id,String name, Integer schoolId, Integer gradeId, Integer classId, String birthday,
                          Integer sex,String parentRole, String parentPhone, String idImg, String faceImg,String patriarch);
    MyResult addExcelHlStudent(Integer id,String name, Integer schoolId, Integer gradeId, Integer classId, String birthday,
                               Integer sex,String parentRole, String parentPhone, String idImg, String faceImg);

    /**
     * 删除学生信息
     * @param ids
     * @return
     */
    MyResult delelteStudent(Integer[] ids);

    /**
     * 学生关系图
     * @param stuId
     * @return
     */
    MyResult familyRelation(Integer stuId);
}
