package com.usoft.sschool_teacher.controllers.app;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.service.IGradeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 陈秋
 * @Date: 2019/5/20 15:52
 * @Version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher/app")
public class ClassGradeController {

    @Autowired
    private IGradeManageService gradeManageService;
    /**
     * 成绩管理
     * @param classId
     * @param teacherId
     * @return
     */
    @GetMapping("/getGrade")
    public MyResult getGrade(String classId,String teacherId,String term,
                             String testName,String subject,String currentPage,String pageSize){
        int thId = SystemParam.getUserId();
        int clsId = 0;
        int start = 0;
        int page = 0;
        if (currentPage!=null && !"".equals(currentPage) && pageSize!=null && !"".equals(pageSize)){
            page = Integer.parseInt(pageSize.trim());
            start = Integer.parseInt(currentPage.trim());
        }else {
            page = 30;
            start = 1;
        }
        try {
            clsId = Integer.parseInt(classId.trim());
            //thId = Integer.parseInt(teacherId.trim());
        }catch (Exception e){
            return new MyResult(2,"上传的ID格式不正确",null);
        }
        Map data = gradeManageService.getGrade(clsId,thId,term,testName,subject,start,page);
        Integer resNum = gradeManageService.getCont(clsId,thId,term,testName,subject,start,page);
        if (data==null){
            return new MyResult(2,"没有数据",null);
        }
        return new MyResult(1,"success",data,start,(resNum+page)/page,page,resNum);
    }

    /**
     * 获取学期
     * @param classId
     * @return
     */
    @GetMapping("/getSemester")
    public MyResult getSemester(String classId){
        return  getGrade(1,classId);
    }

    /**
     * 获取考试类型
     * @param classId
     * @return
     */
    @GetMapping("/getExamType")
    public MyResult getExamType(String classId){
        return getGrade(2,classId);
    }

    public MyResult getGrade(int i,String classId){
        int cId = 0;
        try {
            cId = Integer.parseInt(classId.trim());
        }catch (Exception e){
            return new MyResult(2,"上传的班级ID不正确",null);
        }
        List data = new ArrayList();
        if (i==1){
            data = gradeManageService.getSemester(cId);
        }else if(i==2){
            data = gradeManageService.getExamType(cId);
        }
        if (data.size()==0){
            return new MyResult(2,"没有数据",null);
        }
        return new MyResult(1,"success",data);
    }
}
