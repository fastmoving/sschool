package com.usoft.sschool_teacher.controllers;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.service.IClassManagerService;
import com.usoft.sschool_teacher.util.ClassTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 陈秋
 * @Date: 2019/5/5 13:25
 * @Version 1.0
 * 一键放学
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher/class")
public class ClassOverController {

    @Autowired
    private IClassManagerService classManagerService;

    @Autowired
    private ClassTimeUtil util;

    @PostMapping("/insertClassOver")
    public MyResult insertClassOver(String classIds ,String message){
        if(classIds==null && "".equals(classIds)){
            return new MyResult(2,"上传ID字符串不对","");
        }
        String[] classId = classIds.split(",");
       int i = classManagerService.insertClassOver(classId,message,0,"0");
        if (i==-1){
            return new MyResult(2,"网络延迟","");
        }else if (i==-2){
            return new MyResult(2,"班级没有家长账号","");
        }else if (i>0){
            return new MyResult(1,"success",i);
        }
        return null;
    }

    /**
     * 定时放学
     * @param classIds
     * @param message
     * @return
     */
    @PostMapping("/insertTimeClassOver")
    public MyResult insertTimeClassOver(String classIds ,String message,Long time){
        if(classIds==null && "".equals(classIds)){
            return new MyResult(2,"上传ID字符串不对","");
        }
        String[] classId = classIds.split(",");
        util.timer1(classId,message,time);
        return new MyResult(1,"success","");
    }
}
