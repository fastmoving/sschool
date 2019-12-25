package com.usoft.sschool_teacher.controllers.myselfController;

import com.usoft.sschool_teacher.service.XnSetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 陈秋
 * @Date: 2019/5/16 16:12
 * @Version 1.0
 */
//@RestController
//@RequestMapping("teacher/xnsetmeal")
public class XnSetmealController {



    @Autowired
    private XnSetmealService xnSetmealService;

    @GetMapping("list")
    public Object getXnSetmealOrderList(Integer classId, Integer pageNo, Integer pageSize){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlSWQiOjEsInNjaG9vbElkIjoxLCJ0eXBlIjoidG9rZW4iLCJleHAiOjE1NjA1ODYyNTAsInVzZXJJZCI6NCwiaWF0IjoxNTU3OTk0MjUwfQ.OQscILZoJkeVoPJqFr300ilDJcrqTBXIgepUtH8sn_0";
        return xnSetmealService.getXnSetmealList(classId,token,pageNo,pageSize);
    }
}
