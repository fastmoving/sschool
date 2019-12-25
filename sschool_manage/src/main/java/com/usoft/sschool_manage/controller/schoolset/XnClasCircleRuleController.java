package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnClasCircleRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jijh
 * @Date 2019/5/7 16:29
 */
@RestController
@RequestMapping("manage/circlerule")
public class XnClasCircleRuleController  extends BaseController {

    @Autowired
    private XnClasCircleRuleService xnClasCircleRuleService;


    /**
     * 查看班级圈审核规则
     * @return
     */
    @GetMapping("select")
    public Object selectXnClasCircleRule(){
        return xnClasCircleRuleService.selectXnClasCircleRule();
    }

    /**
     * 编辑班级圈审核规则
     * @param isOpen 是否开启 0否 1是
     * @param checkType 是否需要审核 0否 1.是
     * @return
     */
    @PostMapping("edit")
    public Object editXnClasCircleRule(Integer isOpen, Integer checkType){
        return xnClasCircleRuleService.editXnClasCircleRule(isOpen,checkType);
    }

}
