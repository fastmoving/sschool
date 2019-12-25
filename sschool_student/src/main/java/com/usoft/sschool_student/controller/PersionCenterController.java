package com.usoft.sschool_student.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_student.serivice.PersionCenterService;
import com.usoft.sschool_student.util.SystemParam;
import com.usoft.sschool_student.util.UploadFileUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("student/persion")
public class PersionCenterController {
    @Resource
    private PersionCenterService persionCenterService;
    @Resource
    private UploadFileUtil uploadFileUtil;
    /**
     * 查询孩子信息
     * @return
     */
    @GetMapping("stuInfo")
    public MyResult stuInfo(){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return persionCenterService.stuInfo(schoolId,childId);
    }

    /**
     * 上传头像
     * @param request
     * @return
     */
    @PostMapping("addSphoto")
    public MyResult addSphoto(HttpServletRequest request){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        System.out.println("sdasd"+schoolId);
        return persionCenterService.addSphoto(request,schoolId,childId);
    }

    /**
     * 上传图片
     * @param request
     * @return
     */
    @PostMapping("uploadFile")
    public MyResult uploadFile(HttpServletRequest request){
        return persionCenterService.uploadFile(request);
    }
    @PostMapping("addIdImg")
    public MyResult addIdImg(String idImg){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return persionCenterService.addIdImg(idImg,schoolId,childId);
    }
}
