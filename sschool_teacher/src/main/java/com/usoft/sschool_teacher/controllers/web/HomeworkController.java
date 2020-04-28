package com.usoft.sschool_teacher.controllers.web;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.common.ResDataNum;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.enums.po.WebHomeWorkPo;
import com.usoft.sschool_teacher.service.IHomeWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * author : 陈秋
 * time : 2019-05-12
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher/web/homework")
public class HomeworkController {

    @Autowired
    private IHomeWorkService homeWorkService;
    /**
     * 作业管理web
     * @return
     */
    @GetMapping("/getHomework")
    public ResDataNum getHomework(String teacherId, String hewName, String type, String classIds, String expireTime,
                                            String currentPage,String pageSize){
        ResDataNum result = new ResDataNum();
        Map data = new HashMap();
        int page = 0;//页码
        int start = 0;//起始位置
        try{
           page = Integer.parseInt(pageSize.trim());
           start = Integer.parseInt(currentPage.trim());
        }catch(Exception e){
            result.setStatus(2);
            result.setMessage("上传的分页页面或是下标不对");
            return result;
        }
        try{
            data = homeWorkService.getHomeworkWeb(SystemParam.getUserId(),hewName,type,classIds,expireTime,page,start);
        }catch (Exception e){
            result.setStatus(2);
            result.setMessage("系统故障或是时间格式不对");
            e.printStackTrace();
            return result;
        }
        if (data == null){
            result.setStatus(2);
            result.setMessage("没有数据");
            return result;
        }
        result.setMessage("success");
        result.setStatus(1);
        result.setData(data.get("listData"));
        result.setTotalNumber(Integer.parseInt(data.get("num").toString()));
        return result;
    }


    /**
     *作业详情
     * @param hwmId
     * @return
     */
    @GetMapping("/getHomeworkIfo")
    public MyResult getHomeworkInformation(String hwmId){
        int homeworkId = 0;
        try{
            homeworkId = Integer.parseInt(hwmId.trim());
        }catch(Exception e){
            MyResult res = new MyResult(e,"上传的ID格式不对");
            return res;
        }
       Map<String,Object> resData =  homeWorkService.getHomeworkInformation(homeworkId);
        if (resData == null){
            MyResult res = new MyResult(2,"暂时没有数据",resData);
            return res;
        }
        return new MyResult(1,"success",resData);
    }

    /**
     * 作业审批
     * @param teacherId 教师ID
     * @param state 状态
     * @param stuName 科目
     * @param className 班级名字
     * @param hwmName 作业名
     * @param currentPage 分页
     * @param pageSize 显示条数
     * @param subject 科目
     * @param classId 班级ID
     * @return
     */
    @GetMapping("/getHomeworkStu")
    public MyResult getHomeworkStu(String teacherId,String state,String stuName,String className,
                                   String hwmName,String currentPage,String pageSize,
                                   String subject,String classId){
        int thId = 0;
        Integer states = 0;
        try {
            thId = SystemParam.getUserId();
            if (state!=null && !"".equals(state)){
                states = Integer.parseInt(state.trim());
            }
        }catch (Exception e){
            return new MyResult(e,"没有登录");
        }
        List<Map<String,Object>> data = homeWorkService.getHomeworkmanager(thId,states,stuName, className, hwmName,
                                                                            currentPage,pageSize,subject,classId,1);
        int resNum = homeWorkService.getStuHomeworkEsCount(thId,states,stuName,className,hwmName,subject,classId,1);
        if (data == null || data.size()==0){
            return new MyResult(2,"没有数据","");
        }
        return new MyResult(1,"success",data,0,0,0,resNum);
    }

    /**
     * 发布作业
     */
    @PostMapping("/insertHomework")
    public MyResult insertHomework(@Valid WebHomeWorkPo po){
        int teacherId = 0;
        try {
            teacherId = SystemParam.getUserId();
        }catch (Exception e){
            return new MyResult(2,"请登陆","");
        }
        if (po.getAcceptClass()==null || "".equals(po.getAcceptClass())){
            return new MyResult(2,"请选择班级","");
        }
        String[] classIds = po.getAcceptClass().split(",");
        List<String> classList = Arrays.asList(classIds);
        Set<String> classSet = new HashSet<>(classList);
        if(classList.size() != classSet.size()){
            return new MyResult(2,"不能选择相同的班级",false);
        }
        int i = homeWorkService.insertHomework( po.getHwName(), po.getHwType(), po.getAcceptClass(), po.getSubject(),
                po.getExpireTime(), po.getHwContent(), po.getHwContentImg(), po.getArray());
        if (i==-1){
            return new MyResult(2,"网络延迟","");
        }else if (i==-2){
            return new MyResult(2,"时间格式不正确","");
        }else if (i==-3){
            return new MyResult(2,"该班级没有学生！","");
        }else if (i>0){
            return new MyResult(1,"success",i);
        }
        return null;
    }
}
