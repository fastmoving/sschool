package com.usoft.sschool_student.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_student.serivice.ScoreService;
import com.usoft.sschool_student.util.SystemParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author wlw
 * @data 2019/4/23 0023-16:44
 */
@ResponseBody
@Controller
@RequestMapping("/student/score")
@CrossOrigin
public class ScoreController {
    @Resource
    private ScoreService scoreService;
    @GetMapping("term")
    public MyResult term(){
        return scoreService.term();
    }
    @GetMapping("testName")
    public MyResult testName(String term){
        return scoreService.testName(term);
    }
    /**
     * 学生成绩
     * @param term
     * @param testName
     * @return
     */
    @GetMapping("/oneStudent")
    public MyResult oneStudent(String term, String testName){
        return scoreService.oneStudent(term,testName);
    }
}
