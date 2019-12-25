package com.usoft.sschool_student.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_student.serivice.AttendanceService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("student/attendance")
@CrossOrigin
@RestController
public class AttendanceController {
    @Resource
    private AttendanceService attendanceService;
    @PostMapping("leave")
    public MyResult leave(){
        return null;
    }
}
