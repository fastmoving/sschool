package com.usoft.sschool_teacher.controllers.ClassManagerController;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.controllers.myselfController.MyselfController;
import com.usoft.sschool_teacher.service.IClassManagerService;
import com.usoft.sschool_teacher.service.ITeacherMyselfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 陈秋
 * @Date: 2019/5/17 15:51
 * @Version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher/absent")
public class ClassXnAbsentController extends MyselfController{

    @Resource
    private IClassManagerService classManagerService;

    @Resource
    private ITeacherMyselfService teacherService;

    /**
     * 请假请假详情
     * @param absentId
     * @return
     */
    @GetMapping("/getAbsentIfo")
    public MyResult getAbsentIfo(String absentId){
        int abId = 0;
        try{
            abId = Integer.parseInt(absentId.trim());
        }catch (Exception e){
            return new MyResult(2,"上传ID不对",null);
        }
        Map resData = classManagerService.getAbsentIfo(abId);
        if (resData==null){
            return new MyResult(2,"系统故障",null);
        }
        return new MyResult(1,"success",resData);
    }

    /**
     * 班级审批管理
     * @param teacherId
     * @param classId
     * @param currentPage
     * @param pageSize
     * @param statu
     * @return
     */
    @GetMapping("/getAbsent")
    public MyResult getAbsent(String teacherId,String classId,String currentPage,String pageSize,String statu){
        int thId = SystemParam.getUserId();
        int page = 0;
        int start = 0;
        try {
            //thId = Integer.parseInt(teacherId.trim());
        }catch (Exception e){
            return new MyResult(2,"上传的ID不正确",null);
        }
        if (currentPage!=null && !"".equals(currentPage) &&
                pageSize!=null && !"".equals(pageSize)){
            page = Integer.parseInt(pageSize.trim());
            start = Integer.parseInt(currentPage.trim());
        }else{
            page = 10;
            start = 1;
        }

        //判断领导
        if (classId !=null && "0".equals(classId)){
            List data = teacherService.getMyselfManager();
            if (data.size()==0 || data==null)return new MyResult(4,"您没有这个权限","");
            List res_data = teacherService.getTeacherAbsent(page,start,statu);
            Integer num = teacherService.getTeacherAbsentCount();
            if (res_data.isEmpty())return new MyResult(2,"没有数据","");
            return new MyResult(1,"success",res_data,start,(page+num)/page,page,num);
        }
        //判断班主任
        if (classId !=null && !"".equals(classId) && !"0".equals(classId)){
            Integer class_id = Integer.parseInt(classId.trim());
            List res_data = teacherService.getClassManage(class_id);
            if (res_data==null || res_data.size()==0){
                return new MyResult(4,"您不是班主任","");
            }
        }

        List data =classManagerService.getAbsent(thId,classId,statu,start,page);
        int count = classManagerService.getAbsentCount(thId,classId,statu,start,page);
        if (data.size()==0){
            return new MyResult(2,"没有数据",null);
        }
        return new MyResult(1,"success",data,start,(count+page)/page,page,count);
    }

    /**
     * 审批请假
     * @param absentIds
     * @return
     */
    @PostMapping("/updateAbsent")
    public MyResult updateAbsent(String absentIds,Integer statu){
       if (absentIds==null && "".equals(absentIds)){
           return new MyResult(2,"上传的ID集合为空",null);
       }
       if(statu == null)return new MyResult(2,"请假状态！",null);
       String[] absentId = absentIds.split(",");

        //判断领导
        List data_1 = teacherService.getMyselfManager();
        if (data_1.size()==0 || data_1==null){

            //判断班主任
            List list = new ArrayList<>();
            for (int i=0 ;i<absentId.length;i++ ) {
                list.add(Integer.valueOf(absentId[i]));
            }
            List<Map> data = teacherService.getClassManage();
            List<Integer> class_list = classManagerService.selectMangerClass(list);
            if(data.size()<1)return new MyResult(2,"您不是班主任！",null);
            Boolean class_true = true;
            for (Integer classId:class_list) {
                for (Map map:data) {
                    if(map.containsKey("ID")){
                        if(Integer.valueOf(map.get("ID").toString()) == classId){
                            class_true = false;
                        }
                    }
                }
            }
            if (class_true)return new MyResult(2,"您不是该班班主任！",null);
        }

       int i = classManagerService.updateAbsent(absentId,statu);
       if (i>0){
           return new MyResult(1,"success",null);
       }else if (i == -1){
           return new MyResult(2,"error",null);
       }
        return null;
    }
}
