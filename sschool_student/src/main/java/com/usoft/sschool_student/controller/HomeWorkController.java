package com.usoft.sschool_student.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_student.serivice.HomeWorkService;
import com.usoft.sschool_student.util.SystemParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wlw
 * @data 2019/4/24 0024-9:51
 */
@RequestMapping("/student/homeWork")
@Controller
@ResponseBody
@CrossOrigin
public class HomeWorkController {
    @Resource
    private HomeWorkService homeWorkService;

    /**
     * 查询全部的作业列表
     * @return
     */
    @GetMapping("/serachAll")
    public MyResult serachAll(Integer pageNo,Integer pageSize){
        Integer childId = SystemParam.getChildId();
        return homeWorkService.serachAll(childId,pageNo,pageSize);
    }

    /**
     * 未做作业列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("notdidHwk")
        public MyResult notdidHwk(Integer pageNo,Integer pageSize){
        return homeWorkService.notdidHwk(pageNo,pageSize);
    }

    /**
     * 已做作业列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("didHwk")
        public MyResult didHwk(Integer pageNo,Integer pageSize){
        return homeWorkService.didHwk(pageNo,pageSize);
    }
    /**
     * 作业详情
     * @param hwId
     * @return
     */
    @GetMapping("/homeworkInfo")
    public MyResult homeworkInfo(Integer hwId){
        return homeWorkService.homeworkInfo(hwId);
    }

    /**
     * 提交作业
     * @param hwId
     * @param ans
     * @return
     */
    @PostMapping("/subHomework")
    public MyResult subHomework(Integer hwId, String ans){
        //作业类型为2时上传图片？？
        Integer childId = SystemParam.getChildId();
        return homeWorkService.subHomework(childId,hwId,ans);
    }

    /**
     * 上传答题图片
     * @param request
     * @param hwId
     * @return
     */
    @PostMapping("addHomework")
    public MyResult addHomework(HttpServletRequest request,Integer hwId,String content){
        Integer childId = SystemParam.getChildId();
        return homeWorkService.addHomework(request,hwId,childId,content);
    };

    /**
     * 已做作业详情
     * @param hwId
     * @return
     */
    @GetMapping("hasdidHomeWork")
    public MyResult hasdidHomeWork(Integer hwId){
        return homeWorkService.hasdidHomeWork(hwId);
    }
}
