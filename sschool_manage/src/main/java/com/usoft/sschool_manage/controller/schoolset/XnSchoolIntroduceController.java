package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnSchoolIntroduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author jijh
 * @Date 2019/5/7 11:40
 */
@RestController
@RequestMapping("manage/schoolIntroduce")
public class XnSchoolIntroduceController  extends BaseController {

    @Autowired
    private XnSchoolIntroduceService xnSchoolIntroduceService;

    /**
     * 查看校园简介
     * @return
     */
    @GetMapping("select")
    public Object selectXnSchoolIntroduce(){
        return xnSchoolIntroduceService.selectXnSchoolIntroduce();
    }

    /**
     * 编辑校园简介
     * @param img 图片
     * @param des 描述
     * @return
     */
    @PostMapping("edit")
    public Object editXnSchoolIntroduce(String img, String des){
        return xnSchoolIntroduceService.editXnSchoolIntroduce(img,des);
    }
}
