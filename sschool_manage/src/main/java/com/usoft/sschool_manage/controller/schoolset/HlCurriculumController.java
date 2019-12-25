package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.HlCurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 课程表控制层
 * @Author jijh
 * @Date 2019/4/25 11:38
 */


@RestController
@RequestMapping("/manage/hlcurriculum")
public class HlCurriculumController  extends BaseController {

    @Autowired
    private HlCurriculumService hlCurriculumService;


    /**
     * 课程表列表
     * @param week
     * @param lesson
     * @param subjectId
     * @param teacher
     * @return
     */
    @GetMapping("list")
    public Object listHlCurriculum(Integer classId,Integer week, Integer lesson, Integer subjectId, String teacher,Integer pageNo, Integer pageSize){
        return hlCurriculumService.listCurriculum(classId,week,lesson,subjectId,teacher,pageNo, pageSize);
    }

    /**
     * 添加或者修改课程表
     * @param id 课程表id  修改时使用
     * @param classId 班级id
     * @param cycle 节次
     * @param discipline 科目
     * @param classTeacher 教师
     * @param lessonId 节次ID
     * @param week 周几
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateCurriculum(Integer id, Integer classId, Integer cycle, Integer discipline, Integer classTeacher, Integer lessonId, Integer week){
        return hlCurriculumService.addCurriculum(id,classId,cycle,discipline,classTeacher,lessonId,week);
    }

    /**
     * 删除课程表
     * @param ids
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteCurriculum(Integer[] ids){
        return hlCurriculumService.deleteCurriculum(ids);
    }

}
