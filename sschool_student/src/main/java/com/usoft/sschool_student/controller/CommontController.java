package com.usoft.sschool_student.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_student.serivice.CommontService;
import com.usoft.sschool_student.util.SystemParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("student/commont")
public class CommontController {
    @Resource
    private CommontService commontService;

    /**
     * 查询所有老师
     * @return
     */
    @GetMapping("allTea")
    public MyResult allTea(){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return commontService.allTea(schoolId,childId);
    }

    /**
     * 查询评论
     * @param cid
     * @return
     */
    @GetMapping("searchCommontInfo")
    public MyResult searchCommontInfo(Integer cid){
        Integer schoolId = SystemParam.getSchoolId();
        return commontService.searchCommontInfo(cid,schoolId);
    }

    /**
     * 评价老师
     * @param teaId
     * @param attr1
     * @param star
     * @param des
     * @return
     */
    @PostMapping("commontTea")
    public MyResult commontTea(Integer teaId,Integer attr1,Integer star,String des){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return commontService.commontTea(teaId,star,des,attr1,schoolId,childId);
    }
}
