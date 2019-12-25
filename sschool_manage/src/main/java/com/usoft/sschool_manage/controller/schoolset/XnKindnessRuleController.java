package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnKindnessRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 爱心规则配置
 * @Author jijh
 * @Date 2019/5/7 17:07
 */
@RestController
@RequestMapping("manage/kindnessrule")
public class XnKindnessRuleController  extends BaseController {

    @Autowired
    private XnKindnessRuleService xnKindnessRuleService;

    /**
     * 查看爱心捐赠规则
     * @return
     */
    @GetMapping("select")
    public Object selectXnKindnessRule(){
        return xnKindnessRuleService.selectXnKindnessRule();
    }

    /**
     * 编辑爱心捐赠规则
     * @param isOpen 是否开启 0否 1.是
     * @param perKind 每笔爱心数
     * @return
     */
    @PostMapping("edit")
    public Object editXnKindnessRule(Integer isOpen, Integer perKind){
        return xnKindnessRuleService.editXnKindnessRule(isOpen, perKind);
    }

}
