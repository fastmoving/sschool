package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 班级信息
 * @Author jijh
 * @Date 2019/4/24 9:50
 */
public interface HlSchclassService {


    /**
     * 列出班级信息   根据学校id
     * @param sid 学校id
     * @return
     */
    MyResult listClassMessage(Integer sid, Integer gid, String name);

    /**
     * 条件查询班级信息
     * @param className 班级名称
     * @param teacherName 教师名称
     * @param grade 班级
     * @param schoolId 学校
     * @return
     */
    MyResult listSchclass(String className, String teacherName, Integer grade, Integer schoolId, Integer pageNo, Integer pageSize);


    /**
     * 添加或者编辑班级信息
     * @param id 班级id
     * @param className 班级姓名
     * @param sid 学校id
     * @param teacherId  教师id
     * @param teacherName 教师名称
     * @param grade 班级
     * @param stuNum 学生数量
     * @param classDes 班级描述
     * @param classImg 照片地址
     * @return
     */
    MyResult addOrupdateSchclass(Integer id, String className, Integer sid, Integer teacherId, String teacherName, Integer grade,
                                 Integer stuNum, String classDes, String classImg);


    /**
     * 删除班级信息
     * @param ids 班级id
     * @return
     */
    MyResult deleteSchclass(Integer[] ids);

    /**
     * 导入班级信息
     * @param request
     * @return
     */
    MyResult importSchclass(HttpServletRequest request);

    /**
     * 班级升级
     * @return
     */
    MyResult upgrade();
}
