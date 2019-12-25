package com.usoft.sschool_teacher.controllers.ClassManagerController;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.service.IClassManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 陈秋
 * @Date: 2019/5/5 13:30
 * @Version 1.0
 * 班级管理
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher/classManager")
public class ClassManagerController {

        @Autowired
        private IClassManagerService classManagerService;

        /**
         * 班级管理
         * @param teacherId
         * @return
         */
        @GetMapping("/getClassManager")
        public Map getClassManager(String currentPage,String pageSize){

            int thId = SystemParam.getUserId();
           // int resNum1 = classManagerService.getManagerClassAppCount(teacherId);
            //int resNum2 = classManagerService.getManagerClassCount(teacherId);
            int resNum1 = classManagerService.getManagerClassAppCount(thId);
            int resNum2 = classManagerService.getManagerClassCount(thId);
            int page = 0;
            int start = 0;
            if (currentPage!=null && !"".equals(currentPage) &&
                    pageSize!=null && !"".equals(pageSize)){
                page = Integer.parseInt(pageSize.trim());
                start = Integer.parseInt(currentPage.trim());
            }else{
                page = 50;
                start = 1;
            }
            List<Map<String,Object>> dataList = classManagerService.getManagerClass(thId,page,(start-1)*page);
            Map<String,Object> data = new HashMap<>();
            data.put("status",1);
            data.put("message","success");
            data.put("data",dataList.size()!=0?dataList:"");
            data.put("totalNumber",resNum2+resNum1);
            return data;
        }

    /**
     * 班级相册
     * @param classId
     * @return
     */
    @GetMapping("/getClassPhotos")
        public MyResult getClassPhotos(String classId){

            List data = classManagerService.getClassPhotos(Integer.parseInt(classId.trim()));
            if (data==null || data.size()==0){
                return new MyResult(2,"没有数据",null);
            }
            return new MyResult(1,"success",data);
        }

    /**
     * 班级考勤
     * @param classId
     * @return
     */
    @GetMapping("/getClassChecking")
        public MyResult getClassChecking(String classId){
            int claId = 0;
            try {
                claId = Integer.parseInt(classId.trim());
            }catch (Exception e){
                return new MyResult(e,"上传的ID格式不对");
            }
            Map resData = classManagerService.getClassChecking(claId);
            if (resData==null){
                return new MyResult(2,"网络延迟","");
            }
            return new MyResult(1,"success",resData);
        }

    /**
     * 获取到普通班级
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/getClassManager2")
    public MyResult getClassManager2(String currentPage, String pageSize){
        Integer page = 50;
        List data = classManagerService.getManagerClass2(currentPage,pageSize);
        int resNum2 = classManagerService.getManagerClassCount(SystemParam.getUserId());
        if (data.isEmpty())return new MyResult(2,"没有数据","");
        if(pageSize!=null && !"".equals(pageSize))page = Integer.parseInt(pageSize.trim());
        return new MyResult(1,"success",data,0,(page+resNum2)/page,0,resNum2);
    }
}
