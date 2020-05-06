package com.usoft.sschool_pub.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.serivice.ClassCircleService;
import com.usoft.sschool_pub.util.SystemParam;
import com.usoft.sschool_pub.util.UploadFileUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Year;

@RestController
@CrossOrigin
@RequestMapping("/pub/classCircle")
public class ClassCircleController {
    @Resource
    private ClassCircleService classCircleService;

    /**
     * 查询教师信息
     * @return
     */
    @GetMapping("/teacherInfo")
    public MyResult teacherInfo(){
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId = SystemParam.getUserId();
        return classCircleService.teacherInfo(schoolId,userId);
    }

    /**
     * 查询班级圈
     * @return
     */
    @GetMapping("/searchClassCircle")
    public MyResult searchClassCircle(Integer classId,Integer pageNo,Integer pageSize){
        Integer schoolId = SystemParam.getSchoolId();
        Integer type = SystemParam.getType();
        return classCircleService.searchClassCircle(schoolId,type,classId,pageNo,pageSize);
    }

    /**
     * 查询我发布的班级圈
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/myCricel")
    public MyResult myCricel(Integer pageNo,Integer pageSize){
        return classCircleService.myCricel(pageNo,pageSize);
    }

    /**
     * 选择班级圈的信息
     * @param ccid
     * @return
     */
    @GetMapping("/ClassCircleInfo")
    public MyResult ClassCircleInfo(Integer ccid){
        Integer schoolId = SystemParam.getSchoolId();
        return classCircleService.ClassCircleInfo(ccid,schoolId);
    }

    /**
     * 添加评论
     * @param ccid
     * @param content
     * @param parentId
     * @return
     */
    @PostMapping("/addcomment")
    public MyResult addcomment(Integer ccid,String content,Integer parentId){
        Integer schoolId = SystemParam.getSchoolId();
        Byte type = Byte.valueOf(SystemParam.getType().toString());
        return classCircleService.addcomment(schoolId,ccid,content,parentId,type);
    }
    /**
     * 班级圈点赞
     * @param ccid
     * @return
     */
    @PostMapping("addLike")
    public MyResult addLike(Integer ccid,Integer classId){
        Integer schoolId = SystemParam.getSchoolId();
        Byte type = Byte.valueOf(SystemParam.getType().toString());
        return classCircleService.addLike(ccid,schoolId, Integer.valueOf(type),classId);
    }
    @PostMapping("deleteLike")
    public MyResult deleteLike(Integer ccid){
        Integer schoolId = SystemParam.getSchoolId();
        Byte type = Byte.valueOf(SystemParam.getType().toString());
        return classCircleService.deleteLike(ccid,schoolId, Integer.valueOf(type));
    }
    /**
     * 发表班级圈
     * @param description
     * @return
     */
    @PostMapping("/addClassCircle")
    public MyResult addClassCircle(Integer classId, HttpServletRequest request,String description){
        Integer schoolId = SystemParam.getSchoolId();
        Byte type = Byte.valueOf(SystemParam.getType().toString());
        return classCircleService.addClassCircle(classId,schoolId,type,request,description);
    }

    /**
     * 删除班级圈
     * @param ccid
     * @return
     */
    @PostMapping("deleteClassCircle")
    public MyResult deleteClassCircle(String ccid){
        Integer schoolId = SystemParam.getSchoolId();
        Integer type = SystemParam.getType();
        return classCircleService.deleteClassCircle(ccid,schoolId,type);
    }

    /**
     * 查询班级圈照片
     * @param classId
     * @return
     */
    @GetMapping("searchClassPic")
    public MyResult searchClassPic(Integer classId){
        Integer schoolId = SystemParam.getSchoolId();
        Integer type = SystemParam.getType();
        return classCircleService.searchClassPic(classId,schoolId,type);
    }

    @PostMapping("deleteClassPic")
    public MyResult deleteClassPic(String cpid){
        return classCircleService.deleteClassPic(cpid);
    }
    /**
     * 上传班级圈照片
     * @param classId
     * @param albumName
     * @param imgName
     * @return
     */
    @PostMapping("addClassPic")
    public MyResult addClassPic(Integer classId, HttpServletRequest request, String albumName, String imgName){
        Integer schoolId = SystemParam.getSchoolId();
        Integer type = SystemParam.getType();
        return classCircleService.addClassPic(classId, request,albumName,imgName,schoolId,type);
    }
}
