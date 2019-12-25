package com.usoft.sschool_manage.controller.live;

import com.usoft.sschool_manage.service.live.XnTopQualityClassRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 精品课堂规则
 * @Author jijh
 * @Date 2019/7/9 15:49
 */
@RestController
@RequestMapping("manage/qualityclassrule")
public class XnTopQualityClassRuleController {

    @Autowired
    private XnTopQualityClassRuleService xnTopQualityClassRuleService;


    /**
     * 查看精品课堂规则
     * @return
     */
    @GetMapping("select")
    public Object getXnTopQualityClassRule(){
        return xnTopQualityClassRuleService.selectXnTopQualiyClassRule();
    }


    /**
     * 添加或者修改精品课堂规则
     * @param isPay
     * @param price
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpateXnTopQualityClassRule(Integer isPay, String price){
        return xnTopQualityClassRuleService.addOrUpdateXnTopQualiyClassRule(isPay, price);
    }


}
