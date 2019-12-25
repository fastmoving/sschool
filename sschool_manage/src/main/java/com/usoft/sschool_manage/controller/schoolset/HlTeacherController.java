package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.HlTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 教师控制层
 * @Author jijh
 * @Date 2019/4/29 14:24
 */

@RestController
@RequestMapping("/manage/teacher")
public class HlTeacherController extends BaseController {

    @Autowired
    private HlTeacherService hlTeacherService;


    /**
     * 获取教师基本信息
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("listbase")
    public Object listTeacherBase(String name, Integer pageNo, Integer pageSize){
        return hlTeacherService.listHlTeacherBase(name, pageNo, pageSize);
    }

    /**
     * 根据教师id获取教师所教科目id
     * @param teacherId
     * @return
     */
    @GetMapping("listbytid")
    public Object listSubjectByTeacherId(Integer teacherId){
        return hlTeacherService.listSubjectByTeacher(teacherId);
    }




    /**
     * 获取教师基本信息根据
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("listbysubject")
    public Object listBySubject(String name, String subject, Integer pageNo, Integer pageSize){
        return hlTeacherService.listHlTeacherBySubject(name, subject, pageNo, pageSize);
    }

    /**
     * 教师信息列表
     * @param name 教师名字
     * @param type 教师类型
     * @param subject 科目
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("list")
    public Object listHlTeacher(String name, Integer type, String subject, Integer pageNo, Integer pageSize){
        return hlTeacherService.listHlTeacher(name,type,subject,pageNo,pageSize);
    }

    /**
     * 新增或者修改教师信息
     * @param id 教师id 修改时需要
     * @param name 教师姓名
     * @param birthday 教师生日
     * @param sex 教师性别
     * @param type 类型
     * @param subject 教授科目
     * @param phone 电话号码
     * @param password 密码
     * @param idImg 证件照
     * @param faceImg 风采照
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdataTeacher(Integer id, String name, String birthday, Integer sex, Integer type, String subject, String phone, String password,
                                     String idImg, String faceImg){
        return hlTeacherService.addOrUpdateTeacher(id,name,birthday,sex,type,subject,phone,password,idImg,faceImg);
    }

    /**
     * 删除教师信息
     * @param ids 教师id
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteTeacher(Integer[]  ids){
        //String[] idArray = ids.split(",");

        return hlTeacherService.deleteTeacher(ids);
    }


    /**
     * 修改密码
     * @param id 教师id
     * @param password 密码
     * @return
     */
    @PutMapping("editpass")
    public Object editPassword(Integer id, String password){
        return hlTeacherService.editPassword(id,password);
    }

}
