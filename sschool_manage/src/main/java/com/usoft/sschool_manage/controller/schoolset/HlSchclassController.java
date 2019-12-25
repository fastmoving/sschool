package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.HlSchclassService;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 班级信息控制层
 * @Author jijh
 * @Date 2019/4/24 16:55
 */
@RestController
@RequestMapping("manage/hlschclass")
public class HlSchclassController  extends BaseController {


    @Autowired
    private HlSchclassService hlSchclassService;

    /**
     * 班级基本信息列表
     * @return
     */
    @GetMapping("/listbase")
    public Object listSchclassBase(Integer gid, String name ){
        Integer sid = SystemParam.getSchoolId();
        return hlSchclassService.listClassMessage(sid,gid,name);
    }

    /**
     * 添加或者更新班级信息
     * @param id 班级id
     * @param className 班级名称
     * @param schoolId 学校ID 暂时无用
     * @param teacherId 教师id
     * @param classTeach 班主任名称
     * @param gradeType 年级
     * @param classNum 学生人数
     * @param classDes 班级描述
     * @param classImg 班级图片
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateSchclass(Integer id, String className, Integer schoolId, Integer teacherId,String classTeach, Integer gradeType,
                              Integer classNum, String classDes, String classImg){
        return hlSchclassService.addOrupdateSchclass(id,className,schoolId,teacherId,classTeach,gradeType,classNum,classDes,classImg);
    }

    /**
     * 班级信息列表
     * @param className 班级名称
     * @param classTeach 教师名
     * @param gradeType 年级
     * @param schoolId 学校id
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
             */
    @GetMapping("list")
    public  Object listSchclass(String className, String classTeach, Integer gradeType, Integer schoolId, Integer pageNo, Integer pageSize){
        return hlSchclassService.listSchclass(className,classTeach,gradeType,schoolId,pageNo,pageSize);
    }

    /**
     * 删除班级信息
     * @param ids
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteSchclass(Integer[] ids){
        return hlSchclassService.deleteSchclass(ids);
    }

    /**
     * 学生升级
     * @return
     */
    @PostMapping("upgrade")
    public Object upgrade(){
        return  hlSchclassService.upgrade();
    }

}
