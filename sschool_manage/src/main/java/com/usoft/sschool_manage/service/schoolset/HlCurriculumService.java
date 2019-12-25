package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 课程表管理
 * @Author jijh
 * @Date 2019/4/24 9:26
 */
public interface HlCurriculumService {

    /**
     * 课程表列表
     * @param week 周几
     * @param lesson 节次
     * @param subjectId 科目
     * @param teacher 教师
     * @param classId 班级id
     * @return
     */
    MyResult listCurriculum(Integer classId, Integer week, Integer lesson, Integer subjectId, String teacher, Integer pageNo, Integer pageSize);

    /**
     * 添加或者修改课程表
     * @param id 课程表id
     * @param classId 班级id
     * @param cycle 节次
     * @param discipline 课程id
     * @param classTeacher 教师id
     * @param lessonId 节次id
     * @param week 周几
     * @return
     */
    MyResult addCurriculum(Integer id, Integer classId, Integer cycle, Integer discipline, Integer classTeacher, Integer lessonId, Integer week);

    /**
     * 删除课程表
     * @param ids
     * @return
     */
    MyResult deleteCurriculum(Integer[] ids);

    /**
     * 导入课程表
     * @param request
     * @return
     */
    MyResult importCurriculum(HttpServletRequest request);
}
