package com.usoft.sschool_teacher.controllers.app;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.service.IClassManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 陈秋
 * @Date: 2019/5/21 18:36
 * @Version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher/circle")
public class ClassCircleController {

    @Autowired
    private IClassManagerService classManagerService;

    /**
     * 班级圈审核 管理
     * @param classId
     * @return
     */
    @GetMapping("/getClassCircle")
    public MyResult getClassCircle(String classId,String currentPage,String pageSize,Integer code,Integer status){
        Integer clId = 0;
        int page = 0;
        int start = 0;
        try {
            if (classId !=null && !"".equals(classId)) clId = Integer.parseInt(classId.trim());
            if (classId ==null || "".equals(classId)) clId = null;
            if (pageSize !=null && !"".equals(pageSize))
                page = Integer.parseInt(pageSize.trim());
            else page = 10;
            if (currentPage !=null && !"".equals(currentPage))
                start = Integer.parseInt(currentPage.trim());
            else start =1;
        }catch (Exception e){
            return new MyResult(2,"传入的ID格式不正确",null);
        }
        if (code != null && !"".equals(code)){
            List res_data = classManagerService.getWebClassCircle(clId,start,page,status);
            Integer reums = classManagerService.getClassCircle1Count(clId,status);
            if (res_data==null || res_data.size()==0)return new MyResult(2,"没有数据","");
            return new MyResult(1,"success",res_data,0,0,0,reums);
        }
        if (code == null || "".equals(code)){
            int resNum = classManagerService.getClassCircleCount(clId);
            List data = classManagerService.getClassCircle(clId, page, start);
            if (data == null || data.size() == 0) {
                return new MyResult(2, "没有数据", null);
            }
            return new MyResult(1, "success", data, start, (resNum + page) / page, page, resNum);
        }
        return null;
    }

    /**
     * 班级圈管理
     * @param classId 班级id
     * @param currentPage 页码
     * @param pageSize 显示条数
     * @return
     */
    @GetMapping("/getWebClassCircle")
    public MyResult getWebClassCircle(Integer classId,Integer currentPage,Integer pageSize,Integer status){
        List res_data = classManagerService.getWebClassCircle(classId,currentPage,pageSize,status);
        return new MyResult(1,"",res_data);
    }

    /**
     * 班级圈详情
     * @param circleId
     * @return
     */
    @GetMapping("/getCircleIfo")
    public MyResult getCircleIfo(String circleId){
        int classCircleId = 0;
        try {
            classCircleId = Integer.parseInt(circleId.trim());
        }catch (Exception e){
            return new MyResult(2,"上传的ID格式不对",null);
        }
        List data = classManagerService.getCircleIfo(classCircleId);
        Integer resNum = classManagerService.getCount(classCircleId);
        if (data == null || data.size() == 0){
            return new MyResult(2,"没有数据",null);
        }
        return new MyResult(1,"success",data,0,0,0,resNum);
    }

    /**
     * 审核班级圈
     * @return
     */
    @PostMapping("/updateCircle")
    public MyResult updateCircle(String circleIds,String status){
        if (circleIds==null && "".equals(circleIds)){
            return new MyResult(2,"上传的ID集合为空",null);
        }
        String[] cirIds = circleIds.split(",");
        int statu = 0;
        try {
            statu = Integer.parseInt(status.trim());
        }catch (Exception e){
            return new MyResult(2,"上传的状态码不正确",null);
        }
       int i =  classManagerService.updateCircle(cirIds,statu);
        if (i==-1){
            return new MyResult(2,"网络延迟",null);
        }
        if (i>0){
            return new MyResult(1,"success",null);
        }
        return null;
    }
}
