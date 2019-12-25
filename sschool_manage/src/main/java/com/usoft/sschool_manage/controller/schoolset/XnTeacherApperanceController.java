package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnTeacherApperanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 教师风采控制层
 * @Author jijh
 * @Date 2019/5/6 14:59
 */

@RestController
@RequestMapping("manage/teacherapperance")
public class XnTeacherApperanceController  extends BaseController {

    @Autowired
    private XnTeacherApperanceService xnTeacherApperanceService;


    /**
     * 通过选择教师，异步填充教师数据
     * @param tid 教师id
     * @return
     */
    @GetMapping("getteacher")
    public Object getTeacherMessage(Integer tid){
        return xnTeacherApperanceService.getTeacherMessage(tid);
    }
    /**
     * 教师风采照列表
     * @param name 教师名称
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("list")
    public Object listXnTeacherApperance(String name, Integer pageNo, Integer pageSize){
        return xnTeacherApperanceService.listXnTeacherApperance(name,pageNo, pageSize);
    }

    /**
     * 添加或者修改教师风采照
     * @param id 风采id 修改时需要
     * @param tid 教师id
     * @param subject 科目
     * @param img 风采照地址
     * @param description 介绍
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateTeacherApperance(Integer id, Integer tid, String subject, String img,
                                              String description){
        return xnTeacherApperanceService.addOrUpdateXnTeaxherApperance(id,tid,subject,img,description);
    }
    /**
     * 删除教师风采照
     * @param ids 风采照id
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteXnTeacherApperance(Integer[] ids){
        return xnTeacherApperanceService.deleteXnTeacherApperance(ids);
    }


}
