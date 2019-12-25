package com.usoft.sschool_teacher.controllers;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.service.IClassManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 陈秋
 * @Date: 2019/5/5 13:27
 * @Version 1.0
 * 班级通知
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher/classInform")
public class ClassInformController {

    @Autowired
    private IClassManagerService classManagerService;

    @PostMapping("/insertClassInform")
    public MyResult insertClassInform(String classIds,String message){
        if (classIds==null || "".equals(classIds)){
            return new MyResult(2,"上传的班级ID为空","");
        }
        String[] classId = classIds.split(",");
        int i = classManagerService.insertClassInform(classId,message);
        if (i==-1){
            return new MyResult(2,"上传的班级ID为空","");
        }else if (i==-2){
            return new MyResult(2,"班级没有家长账号","");
        }else if (i==-3){
            return new MyResult(2,"网络延迟","");
        }else if (i>0){
            return new MyResult(1,"success",i);
        }
        return null;
    }
}
