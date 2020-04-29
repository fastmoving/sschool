package com.usoft.sschool_teacher.controllers;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.service.IClassManagerService;
import com.usoft.sschool_teacher.util.ClassTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 一键放学
     * @param classIds
     * @param message
     * @return
     */
    @PostMapping("/insertClassOver")
    public MyResult insertClassOver(String classIds ,String message){
        int i = 1;
        if(classIds !=null && !"".equals(classIds)) {//指定班级一键放学
            String[] classId = classIds.split(",");
            //判断班级id是否符合要求
            for (int j=0;j<classId.length;j++){
                Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
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
    public MyResult insertTimeClassOver(@RequestParam("classIds") String classIds ,
                                        @RequestParam("message")String message,
                                        @RequestParam("time")Long time){
        //判断时间的正确性
        if(time.longValue()<System.currentTimeMillis()){
            return new MyResult(2,"定时不能小于当前时间",false);
        }

        String[] classId = classIds.split(",");
//        String res = this.classManagerService.addTimingUpSchool(classId,time,message);
//        if(res != null){
//            return new MyResult(500,res,"");
//        }
        util.timer1(classId,message,time);
        return new MyResult(1,"success","操作成功");
    }

    /**
     * 轮询时间表，是否存在定时放学
     *
     * 每个半小时扫描一次
     */
//    @Scheduled(cron = "* * 0/30 * * ?")
//    public void getDate(){
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                classManagerService.timingTask();
//            }
//        };
//        Thread thread = new Thread(runnable);
//        thread.start();
//    }
}
