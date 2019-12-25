package com.usoft.sschool_pub.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_pub.serivice.CurriculumService;
import com.usoft.sschool_pub.util.SystemParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wlw
 * @data 2019/4/28 0028-16:27
 */
@RestController
@RequestMapping("/pub/curriculum")
@CrossOrigin
public class CurriculumController {
    @Resource
    private CurriculumService curriculumService;

    /**
     * 根据学校id和学生id查询课表
     * @return
     */
    @GetMapping("studentCurri")
    public MyResult studentCurri(Integer week){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return curriculumService.stuCurri(schoolId,childId,week);
    }

    /**
     * 学生web端查询课表
     * @return
     */
    @GetMapping("webStudentCurri")
    public MyResult webStudentCurri(){
        return curriculumService.webStudentCurri();
    }
    /**
     * 教师课表
     * @return
     */
    @GetMapping("teacherCurri")
    public MyResult teacherCurr(){
        Integer schoolId = SystemParam.getSchoolId();
        return curriculumService.teacherCurr(schoolId);
    }
}
