package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.HlStudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 学生信息控制层
 * @Author jijh
 * @Date 2019/4/26 15:42
 */
@RestController
@RequestMapping("manage/student")
public class HlStudentInfoController  extends BaseController {

    @Autowired
    private HlStudentInfoService hlStudentInfoService;


    /**
     * 学生基本信息列表
     * @param  cid 班级id
     * @param name 学生姓名模糊查询
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("listbase")
    public Object listStudentInfoBase(Integer cid ,String name,Integer pageNo, Integer pageSize){
        return hlStudentInfoService.listStuInfoBase(cid, name,pageNo, pageSize);

    }


    /**
     * 学生信息查看  家长号码，班级
     * @param id
     * @return
     */
    @GetMapping("listinfo")
    public Object listStudentInfo(Integer id ){
        return hlStudentInfoService.selectStudentInfo(id);
    }

    /**
     * 学生信息列表
     * @param studentName
     * @param gradeType
     * @param classId
     * @param parentPhone
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("list")
    public Object listHlStudentInfo(String studentName, Integer gradeType, Integer classId, String parentPhone,
                                    Integer pageNo, Integer pageSize){
        return hlStudentInfoService.listHlStudentInfo(studentName,gradeType,classId,parentPhone,pageNo,pageSize);

    }

    /**
     * 添加或者修改学生信息
     * @param id 学生id (修改时传)
     * @param name (学生姓名)
     * @param schoolId (学校id)
     * @param gradeId (年级id)
     * @param classId  (班级id)
     * @param birthday 生日
     * @param sex 性别
     * @param parentPhone 家长号码
     * @param idImg 证件照
     * @param faceImg 风采照
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateStudentInfo(Integer id,String name, Integer schoolId, Integer gradeId, Integer classId, String birthday,
                                         Integer sex,String parentRole, String parentPhone, String idImg, String faceImg,String patriarch){
        return hlStudentInfoService.addOrUpdateHlStudent(id,name,schoolId,gradeId,classId,birthday,sex, parentRole,parentPhone,idImg,faceImg,patriarch);
    }

    /**
     * 删除学生信息
     * @param ids
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteStudentInfo(Integer[] ids){
        return hlStudentInfoService.delelteStudent(ids);
    }


    @GetMapping("getrelation")
    public Object getFamilyRelation(Integer stuId){
        return hlStudentInfoService.familyRelation(stuId);
    }

}
