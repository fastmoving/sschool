package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.CfDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jijh
 * @Date 2019/4/22 18:36
 */

@RestController
@RequestMapping("manage/cfdepart")
public class CfDepartmentController  extends BaseController {
    @Autowired
    private CfDepartmentService cfDepartmentService;

    /**
     * 所有学校列表
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("/listall")
    public Object listAllSchool(String name, Integer pageNo, Integer pageSize){
        return cfDepartmentService.listAllSchool(name,pageNo, pageSize);
    }
}
