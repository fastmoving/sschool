package com.usoft.sschool_student.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_student.serivice.OpinionService;
import com.usoft.sschool_student.util.SystemParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/student/evaluate")
public class OpinionController {
    @Resource
    private OpinionService opinionService;
    @GetMapping("searchTeacher")
    public MyResult searchTeacher(){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return opinionService.searchTeacher(schoolId,childId);
    }

    /**
     * 我要请假
     * @param absentType
     * @param beginDate
     * @param endDate
     * @param attr2
     * @param attr1
     * @return
     */
    @PostMapping("leave")
    public MyResult leave(Integer absentType,String beginDate,String endDate,String attr2,String attr1){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return opinionService.leave(schoolId,childId,absentType,beginDate,endDate,attr2,attr1);
    }
    @GetMapping("searchMyLeave")
    public MyResult searchMyLeave(){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return opinionService.searchMyLeave(schoolId,childId);
    }
    @GetMapping("getMyTimeBook")
    MyResult getMyTimeBook(String times){
        return opinionService.getMyTimeBook(times);
    }
}
