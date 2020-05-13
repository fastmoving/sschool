package com.usoft.sschool_teacher.controllers.myselfController;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.service.ITeacherMyselfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 陈秋
 * @Date: 2019/5/16 11:33
 * @Version 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher/myself")
public class MyselfController {

    @Autowired
    private ITeacherMyselfService teacherService;

    //private int teacherId = SystemParam.getUserId();
    /**
     * 教师信息
     * @param teacherId
     * @return
     */
    @GetMapping("/getMyselfIfo")
    public MyResult getMyselfIfo(String teacherId){
        int thId = SystemParam.getUserId();
        if (teacherService.getMyselfIfo(thId)==null){
            return new MyResult(2,"error",teacherService.getMyselfIfo(thId));
        }
        return new MyResult(1,"success",teacherService.getMyselfIfo(thId).get(0));
    }

    /**
     * 修改证件照
     * @param faceImg 头像
     * @param idImg 风采照
     * @return
     */
    @PostMapping("/updateMyself")
    public MyResult updateMyself(String faceImg,String idImg,String code){
        Integer teacherId = 0;
        try {
            teacherId = SystemParam.getUserId();
        }catch (Exception e){
            return new MyResult(2,"你还没有登陆","");
        }
        int i = teacherService.updateMyself(teacherId,faceImg,idImg,code);
        if (i>0){
            return new MyResult(1,"success","");
        }else if (i==-1){
            return  new MyResult(2,"网络延迟","");
        }else if(i == -3){
            return  new MyResult(2,"最多只能上传三张风采照！","");
        }
        return null;
    }

    /**
     * 修改证件照 (app端)
     * * @param faceImg 头像
     * * @param idImg 风采照
     * @return
     */
    @PostMapping("/updateMyselfApp")
    public MyResult updateMyselfApp(String faceImg,String idImg){
        try {
           SystemParam.getUserId();
           return teacherService.updateMyselfApp(faceImg,idImg);
        }catch (Exception e){
            return new MyResult(2,"你还没有登陆","");
        }
    }

    /**
     * 查找学校领导
     * @return
     */
    @GetMapping("/getSchoolManager")
    public Object getSchoolManager(){
        Map data = teacherService.getSchoolManager();
        if (data == null ){
            return new MyResult(2,"没有数据","");
        }
        return new MyResult(1,"success",data);
    }
    /**
     * 教师请假
     * @param type
     * @param begin
     * @param end
     * @param times
     * @param thing
     * @return
     */
    @PostMapping("/insertAbsent")
    public MyResult insertAbsent(String type,String begin,String end,String times,
                            String thing){
        int thId = SystemParam.getUserId();
        int types = 0;
        try {
            //thId = Integer.parseInt(teacherId.trim());
            types = Integer.parseInt(type.trim());
        }catch (Exception e){
            return new MyResult(2,"上传教师ID或是请假类型不对",null);
        }
        int i = teacherService.insertAbsent(thId,types,begin,end,times,thing);
        if (i>0){
            return new MyResult(1,"success",null);
        }else if (i==-1){
            return new MyResult(2,"上传的时间不正确",null);
        }else if (i==-2){
            return new MyResult(2,"网络延迟",null);
        }
        return null;
    }

    /**
     * 我的考勤
     */
    @RequestMapping("/getTimeBook")
    public MyResult getMyTimeBook(String times){
        int thId = SystemParam.getUserId();
        Map data = teacherService.getMyTimeBook(thId,times);
        if (data==null || "".equals(data)){
            return new MyResult(1,"没有数据",new ArrayList());
        }
        return new MyResult(1,"success",data);
    }

    /**
     * 教师请假信息
     * @param pageSize
     * @param currentPage
     * @return
     */
    @GetMapping("/getTeacherAbsent")
    public MyResult getTeacherAbsent(String pageSize,String currentPage){
        int page = 0;
        int start = 0;
        if (pageSize!=null && !"".equals(pageSize))page = Integer.parseInt(pageSize.trim());
        else page = 10;
        if (currentPage!=null && !"".equals(currentPage))start = Integer.parseInt(currentPage.trim());
        else start = 1;
        List data = teacherService.getMyselfManager();
        if (data.isEmpty()){
            return new MyResult(2,"你没有这个权限","");
        }
        List res_data = teacherService.getTeacherAbsent(page,start,"1");
        Integer num = teacherService.getTeacherAbsentCount();
        if (res_data.isEmpty()){
            return new MyResult(2,"没有数据","");
        }
        return new MyResult(1,"success",res_data,start,(page+num)/page,page,num);
    }

    /**
     * 我的爱心
     * @param teacherId
     * @return
     */
    @RequestMapping("getMyKindness")
    public MyResult getMyKindness(String teacherId,String pageSize,String currentPage,String goodsStatus){
        int thId = SystemParam.getUserId();
        int status = 0;
        try {
            //thId = Integer.parseInt(teacherId.trim());
            status = Integer.parseInt(goodsStatus.trim());
        }catch (Exception e){
            return new MyResult(2,"上传的ID格式不对或是状态吗不对",null);
        }
       List resData =  teacherService.getMyKindness(thId,pageSize,currentPage,status);
        if (resData==null || resData.size()==0){
            return new MyResult(2,"没有数据",resData);
        }
        return new MyResult(1,"success",resData);
    }

    /**
     * 更改我的爱心状态
     * @param kindnessId
     * @return
     */
    @PutMapping("/updateKindness")
    public MyResult updateKindness(String kindnessId){
        int kId = 0;
        try {
            kId = Integer.parseInt(kindnessId.trim());
        }catch (Exception e){
            return new MyResult(2,"上传的ID格式不对",null);
        }
        int i = teacherService.updateKindness(kId);
        if (i==-1){
            return new MyResult(2,"网络延迟","");
        }else if (i>0){
            return new MyResult(1,"success",i);
        }
        return null;
    }

    /**
     * 我的爱心详情
     */
    @RequestMapping("/getKindnessIfo")
    public MyResult getKindnessIfo(String kindnessId){
        int kId = 0;
        try {
            kId = Integer.parseInt(kindnessId.trim());
        }catch (Exception e){
            return new MyResult(2,"上传的ID格式不对",null);
        }
        return null;
    }

    /**
     * 我的课程
     */
    @RequestMapping("/getCurriculum")
    public MyResult getCurriculum(){
        try {
            List resData = teacherService.getCurriculum();
            if (resData==null || resData.size()==0){
                return new MyResult(2,"没有数据","");
            }
            return new MyResult(1,"success",resData);
        }catch (Exception e){
            return  new MyResult(2,"网络延迟","");
        }
    }
    @RequestMapping("/getWebCurriculum")
    public MyResult getWebCurriculum(Integer classId){
        try {
            List res_data = teacherService.getWebCurriculum(classId);
            if (res_data == null || res_data.size() == 0)return new MyResult(2,"没有数据","");
            return new MyResult(1,"success",res_data);
        }catch (Exception e){
            return new MyResult(2,"网络延迟","");
        }
    }

    /**
     * 我的菜谱
     * @return
     */
    @RequestMapping("/getMenu")
    public MyResult getMenu(){

        List data = teacherService.getMenu();
        if (data == null || data.size()==0){
            return new MyResult(2,"没有数据","");
        }
        return new MyResult(1,"success",data);
    }

    @GetMapping("/getAppMenu")
    public MyResult getAppMenu(){
        List data = teacherService.getAppMenu();
        if (data == null || data.size()==0){
            return new MyResult(2,"没有数据","");
        }
        return new MyResult(1,"success",data);
    }

    /**
     * 我的已购课程
     */
    @GetMapping("/getVideo")
    public MyResult getVideo(String pageSize,String currentPage){
        int page = 0;
        int start = 0;
        try {
            SystemParam.getUserId();
            page = Integer.parseInt(pageSize.trim());
            start = Integer.parseInt(currentPage.trim());
        }catch (Exception e){
            return new MyResult(2,"您还没有登录,上传分页数据","");
        }
        List data = teacherService.getVideo(page,start);
        int resSum = teacherService.getTeacherVideoCount();
        if (data==null || data.size()==0){
            return new MyResult(2,"没有数据","");
        }
        return new MyResult(1,"success",data,start,(resSum+page)/page,page,resSum);
    }

    /**
     * 班主任 管理班级
     */
    @GetMapping("/getClassManager")
    public MyResult getClassManager(){
        try {
            SystemParam.getUserId();
        }catch (Exception e){
            return new MyResult(2,"请登录","");
        }
        List<Map> data = teacherService.getClassManage();
        if (data==null || data.size()==0){
            return new MyResult(2,"你不是班主任","");
        }
        return new MyResult(1,"success",data);
    }

    /**
     * 订单套餐
     */
    @GetMapping("/getOrderIfo")
    public MyResult getOrderIfo(Integer classId,Integer currentPage ,Integer pageSize){
        Integer page = 0;
        Integer start = 0;
        if(pageSize == null)page = 10;
        else page = pageSize;
        if(currentPage == null)start = 1;
        else start = currentPage;
        try {
            SystemParam.getUserId();
        }catch (Exception e){
            return new MyResult(2,"请登录","");
        }
        List<Map> data = teacherService.getOrderClassIfo(classId,(start-1)*page,page);
        int resNum = teacherService.listXnSetmealOrderCount(classId);
        if (data==null || data.size()==0){
            return new MyResult(2,"没有数据","");
        }
        return new MyResult(1,"success",data,0,(resNum+page)/page,0,resNum);
    }

    /**
     * 获取教师自己品论
     * @return
     */
    @GetMapping("getMyselfEvaluation")
    public MyResult getMyselfEvaluation(Integer pageNo,Integer pageSize){
        return this.teacherService.getMyselfEvaluation(pageNo,pageSize);
    }
}
