package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.BasicDataSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基本数据选择列表
 * @Author jijh
 * @Date 2019/4/23 14:47
 */

@RestController
@RequestMapping("/manage/basicdata")
public class BasicDataSelectController  extends BaseController {

    @Autowired
    private BasicDataSelectService basicDataSelectService;

    /**
     * 基本数据
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("listfi")
    public Object listFirstLevel(Integer pageNo, Integer pageSize){
        return basicDataSelectService.findAll(pageNo,pageSize);
    }

    /**
     * 基本数据列表
     * @param enumId
     * @return
     */
    @GetMapping("listse")
    public Object listSecondLevel(Integer enumId){
        return basicDataSelectService.findByEnumId(enumId);
    }
}
