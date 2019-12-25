package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.HlCurriculumsetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课时控制层
 * @Author jijh
 * @Date 2019/4/23 16:54
 */
@RestController
@RequestMapping("manage/curriculumset")
public class HlCurriculumsetController  extends BaseController {

    @Autowired
    private HlCurriculumsetService hlCurriculumsetService;



    @GetMapping("list")
public Object listCurriculumset(Integer sid){
    return hlCurriculumsetService.listCurriculumsetMessage(sid);
}
}
