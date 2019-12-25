package com.usoft.sschool_teacher.controllers.ClassManagerController.ClassHomeWork;

import com.usoft.smartschool.pojo.HlTeacher;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.NumberKit;
import com.usoft.sschool_teacher.common.ResultData;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.service.IHomeWorkService;
import com.usoft.sschool_teacher.util.UploadFileUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 陈秋
 * @Date: 2019/5/5 13:29
 * @Version 1.0
 * 作业发布
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher/homerWork")
public class HomerWorkController {

    @Autowired
    private IHomeWorkService homeWorkService;

    @Autowired
    private UploadFileUtil uploadFileUtil;

    /**
     * 发布作业
     * @param teacherId
     * @param hwName
     * @param hwType
     * @param acceptClass
     * @param subject
     * @param expireTime
     * @param hwContent
     * @param hwContentImg
     * @return
     */
    @PostMapping("/insertHomework")
    public ResultData insertHomeWork(String teacherId,String hwName,Integer hwType,String acceptClass,String subject,
                                    String expireTime,String hwContent,String hwContentImg){
        ResultData result = new ResultData();
        int thId = SystemParam.getUserId();
        /*int i = homeWorkService.insertHomeWork(teacherId, hwName, hwType, acceptClass, subject,
                                                expireTime, hwContent, hwContentImg);*/
        int i = homeWorkService.insertHomeWork(thId, hwName, hwType, acceptClass, subject,
                expireTime, hwContent, hwContentImg);
        if (i>0){
            result.setStatus(1);
            result.setMessage("success");
            result.setData(i);
            return result;
        }else if (i == -1){
            result.setStatus(2);
            result.setMessage("系统故障");
            return result;
        }else if(i == -2){
            result.setStatus(2);
            result.setMessage("教师ID不能为空");
            return result;
        }else if (i == -3){
            result.setStatus(2);
            result.setMessage("接收班级不能为空");
            return result;
        }else if (i == -6){
            result.setStatus(2);
            result.setMessage("时间格式不正确");
            return result;
        }else if(i == -10){
            result.setStatus(2);
            result.setMessage("班级不存在");
            return result;
        }else if (i == -8){
            result.setStatus(2);
            result.setMessage("班级上传ID格式不对");
            return result;
        }else if (i == -9){
            result.setStatus(2);
            result.setMessage("请选择班级");
            return result;
        }
        return null;
    }

    /**
     * 修改作业
     * @param hmwId
     * @param thId
     * @param hwName
     * @param hwType
     * @param acceptClass
     * @param subject
     * @param expireTime
     * @param hwContent
     * @param hwContentImg
     * @return
     */
    @PostMapping("/updateHomework")
    public ResultData updateHomework(String hmwId,String thId,String hwName,String hwType,String acceptClass,String subject,
                                     String expireTime,String hwContent,String hwContentImg){
        ResultData res = new ResultData();
        int homeworkId = 0;
        int teacherId = SystemParam.getUserId();
        int homework = 0;
        try {
            homeworkId = Integer.parseInt(hmwId.trim());
        }catch(Exception e){
            res.setMessage("上传的ID不对");
            res.setStatus(2);
            return res;
        }
        if (thId!=null && !"".equals(thId) && hwName!=null && !"".equals(hwType)){
            try {
                //teacherId = Integer.parseInt(thId.trim());
                homework = Integer.parseInt(hwType.trim());
            }catch (Exception e){
                res.setStatus(2);
                res.setMessage("上传教师或作业类型ID格式不对");
                return res;
            }
        }

        int i = homeWorkService.updateHomework(homeworkId,teacherId, hwName, homework, acceptClass, subject,
                                        expireTime, hwContent, hwContentImg);
        if (i>0){
            res.setStatus(1);
            res.setMessage("操作成功");
            return res;
        }else if (i == -6){
            res.setStatus(2);
            res.setMessage("时间格式不正确");
            return res;
        }else if (i == -1){
            res.setStatus(2);
            res.setMessage("系统故障");
            return res;
        }
        return res;
    }

    /**
     * 提交选择题
     * @param hwid
     * @param title
     * @param answerA
     * @param answerB
     * @param answerC
     * @param answerD
     * @param rightAnswer
     * @return
     */
    @PostMapping("/insertHomeworkTitle")
    public ResultData insertHomeworkTitle(Integer hwid,String title,String answerA,String answerB,
                                          String answerC,String answerD,Integer rightAnswer){
        ResultData res = new ResultData();
        int i = homeWorkService.insertHomeworkTitle(hwid,title,answerA,answerB,
                                        answerC,answerD,rightAnswer);
        if (i>0){
            res.setStatus(1);
            res.setMessage("success");
            return res;
        }else if(i == -4){
            res.setStatus(2);
            res.setMessage("选择题题目不能为空");
            return res;
        }else if (i == -5) {
            res.setStatus(2);
            res.setMessage("学者题题目不能为空");
            return res;
        }else if (i == -7){
            res.setStatus(2);
            res.setMessage("作业ID不能为空");
            return res;
        }else if (i == -1){
            res.setStatus(2);
            res.setMessage("系统故障");
            return res;
        }
        return null;
    }

    /**
     * 上传图片
     * @param request
     * @return
     */
    @PostMapping("/uploadImg")
    public MyResult uploadImg(HttpServletRequest request, @Param("state")Integer state){

        return uploadFileUtil.uploadFiles(request);
    }

    /**
     * 选择科目
     * @param teacherId
     * @return
     */
    @GetMapping("/getSubject")
    public ResultData getSubject(String teacherId){
        ResultData res = new ResultData();
        int thId = SystemParam.getUserId();
        //HlTeacher teacher = homeWorkService.getSubject(teacherId);
        HlTeacher teacher = homeWorkService.getSubject(thId);
        if (teacher!=null){
            res.setStatus(1);
            res.setMessage("success");
            res.setData(teacher);
            return res;
        }
        res.setStatus(1);
        res.setMessage("老师你还没有选择你的科目");
        res.setData("");
        return res;
    }

    /**
     * 作业管理
     * @param teacherId
     * @return
     */
    @GetMapping("/getHomeworkManager")
    public ResultData getHomeworkManager(String teacherId,Integer state,String stuName,String className,
                                         String hwmName,String currentPage,String pageSize,
                                         String subject,String classId,HttpServletRequest request){
        ResultData result = new ResultData();
        int page = 0;
        if (pageSize!=null && !"".equals(pageSize))page = Integer.parseInt(pageSize);
        else page = 10;
        int thId = 0;
        try {
            thId = SystemParam.getUserId();
        }catch (Exception e){
            result.setStatus(2);
            result.setMessage("没有登陆");
            result.setData("");
            return result;
        }
        /*List<Map<String,Object>> data = homeWorkService.getHomeworkmanager(teacherId,state,stuName, className, hwmName, currentPage,pageSize);
        int resNum = homeWorkService.getStuHomeworkEsCount(teacherId,state);*/
        HttpSession session = request.getSession();
        session.setAttribute("subject",subject);
        session.setAttribute("classId",classId);
        List<Map<String,Object>> data = homeWorkService.getHomeworkmanager(thId,state,stuName, className,
                                                                            hwmName, currentPage,pageSize,subject,classId,0);
        int resNum = homeWorkService.getStuHomeworkEsCount(thId,state,stuName,className,hwmName,subject,classId,0);
        if (data == null || data.size()==0){
            result.setStatus(2);
            result.setMessage("暂无数据");
            result.setData("");
            return result;
        }
        result.setMessage("success");
        result.setStatus(1);
        result.setData(data);
        result.setTotalNumber((resNum+page)/page);
        return result;
    }

    /**
     * 作业详情
     * @param homeworkId
     * @return
     */
    @GetMapping("/getHomeworkInformation")
    public ResultData getHomeworkInformation(String homeworkId,String studentId){
        ResultData res = new ResultData();
        int hwId = 0;
        int stId = 0;
        if (homeworkId == null || "".equals(homeworkId)){
            res.setStatus(2);
            res.setMessage("没有传入作业id");
            return res;
        }
        if (studentId == null || "".equals(studentId)){
            res.setStatus(2);
            res.setMessage("没有传入学生id");
            return res;
        }
        try {
             hwId = Integer.parseInt(homeworkId.trim());
             stId = Integer.parseInt(studentId.trim());
            //homeWorkService.getHomeworkInformation(hwId,stId);
        }catch(Exception e){
            res.setMessage("传入的参数格式不对");
            res.setStatus(2);
            return res;
        }
       Map<String,Object> resData =  homeWorkService.getHomeworkInformation(hwId,stId);
        if (resData.containsKey("msg")){
            res.setStatus(2);
            res.setMessage(resData.get("msg").toString());
            return res;
        }
        res.setStatus(1);
        res.setMessage("success");
        res.setData(resData);
        return res;
    }

    /**
     * 教师评语
     * @param imgPath
     * @param comment
     * @return
     */
    @PostMapping("/insertComment")
    public ResultData insertComment(String imgPath,String comment,String teacherId,String stuId,String hwmId){
        ResultData res = new ResultData();
        int tId = SystemParam.getUserId();
        int sId = 0;
        int hId = 0;
        try {
            //tId = Integer.parseInt(teacherId.trim());
            sId = Integer.parseInt(stuId.trim());
            hId = Integer.parseInt(hwmId.trim());
        }catch (Exception e){
            res.setMessage("id上传的格式不对");
            res.setStatus(2);
            return  res;
        }

       int i =  homeWorkService.insertComment(imgPath,comment,tId,sId,hId);
        if (i == -2){
            res.setStatus(2);
            res.setMessage("没有找到相关学生作业");
            return res;
        }else if(i == -1){
            res.setStatus(2);
            res.setMessage("系统故障");
            return res;
        }else if (i>0){
            res.setStatus(1);
            res.setMessage("success");
            return res;
        }
        return res;
    }

    /**
     * 教师批量评语
     * @return
     */
    @PostMapping("/insertComments")
    public ResultData insertComments(String imgPath,String comment,String teacherId,String stuHmwIds,HttpServletRequest request){
        ResultData res = new ResultData();
        int tId = SystemParam.getUserId();
        /*try{
            //tId = Integer.parseInt(teacherId.trim());
        }catch (Exception e){
            res.setStatus(2);
            res.setMessage("上传教师id错误");
            return  res;
        }*/
        HttpSession session = request.getSession();
        Map data = new HashMap();
        data.put("classId",session.getAttribute("classId"));
        data.put("subject",session.getAttribute("subject"));
       int i =  homeWorkService.insertComments(imgPath,comment,tId,stuHmwIds);
        if(i == -2){
            res.setStatus(2);
            res.setMessage("系统故障");
            return res;
        }else if (i>0){
            res.setStatus(1);
            res.setMessage("success");
            res.setData(data);
            return res;
        }
        return res;
    }

    /**
     * 发布作业选择班级
     */
    @GetMapping("/getClasses")
    public MyResult getClasses(String subject){
        List data = homeWorkService.getClasses(subject);
        if (data.size()==0){
            return new MyResult(2,"没有数据","");
        }
        return new MyResult(1,"success",data);
    }
}
