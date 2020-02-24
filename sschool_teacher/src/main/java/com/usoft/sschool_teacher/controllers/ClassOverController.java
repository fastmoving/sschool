package com.usoft.sschool_teacher.controllers;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.service.IClassManagerService;
import com.usoft.sschool_teacher.util.ClassTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        int i = 1;
        if(classIds !=null && !"".equals(classIds)) {//指定班级一键放学
            String[] classId = classIds.split(",");
            //判断班级id是否符合要求
            for (int j=0;j<classId.length;j++){
                Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//这个也行
                Matcher isNum = pattern.matcher(classId[j]);
                if (!isNum.matches()) {
                    return new MyResult(2,"上传ID字符串不对","");
                }
            }
            i = classManagerService.insertClassOver(classId, message, 0, "0");
        }else { //全校学生放学
            Integer schoolId = SystemParam.getSchoolId();
            String[] classId = classManagerService.getSchoolClassId(schoolId);
            i = classManagerService.insertClassOver(classId, message, 0, "0");
        }
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
