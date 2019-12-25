package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnIntrestClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author jijh
 * @Date 2019/5/6 13:57
 */

@RestController
@RequestMapping("manage/intrestclass")
public class XnIntrestClassController  extends BaseController {

    @Autowired
    private XnIntrestClassService xnIntrestClassService;
    /**
     * 兴趣班列表
     * @param className 兴趣班名称
     * @return
     */
    @GetMapping("list")
    public Object listXnIntrestClass(String className, Integer pageNo, Integer pageSize){
        return xnIntrestClassService.listXnIntrestClass(className,pageNo,pageSize);
    }

    /**
     * 添加或者修改兴趣班
     * @param id  兴趣班id  修改时需要
     * @param className 兴趣班名称
     * @param tid 教师id
     * @param teacherName 教师名称
     * @param beginDate 开班时间
     * @param classNum 学生数量
     * @param img 班级图片
     * @param classDes 兴趣班简介
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateXnIntrestClass(Integer id, String className, Integer tid, String teacherName, String beginDate, Integer classNum,String img, String classDes,String price){
        return xnIntrestClassService.addOrUpdateXnIntrestClass(id,className,tid,teacherName,beginDate,classNum,img,classDes,price);
    }

    /**
     * 删除兴趣班
     * @param ids
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteXnIntrestClass(Integer[] ids){
        return xnIntrestClassService.deleteXnIntrestClass(ids);
    }

    /**
     * 兴趣班学生列表
     * @param intrestId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("listintreststudent")
    public Object listIntrestStudent(Integer intrestId, Integer pageNo, Integer pageSize){
        return xnIntrestClassService.listIntrestClassStudent(intrestId, pageNo, pageSize);
    }

    /**
     * 添加或者修改兴趣班学生
     * @param id
     * @param sid
     * @param parentPhone
     * @param entryType
     * @return
     */
    @PostMapping("addorupdatestudent")
    public Object addOrUpdateIntrestStudent(Integer intrestId,Integer id, Integer sid, String parentPhone, Integer entryType){
        return xnIntrestClassService.addOrupdateStudent(intrestId, id, sid, parentPhone, entryType);
    }

    /**
     * 删除兴趣班学生
     * @param ids
     * @return
     */
    @DeleteMapping("deletestudent")
    public Object deleteXnIntrestStudent(Integer[] ids){
        return xnIntrestClassService.deleteIntrestClassStudent(ids);
    }

}
